package kerebus.kerepic.ui

import android.graphics.{BitmapFactory, Bitmap}
import java.net.{HttpURLConnection, URL}
import java.io._
import android.util.Log
import android.widget.ImageView
import java.lang.ref.WeakReference
import kerebus.kerepic.cache.{FileCache, MemoryCache}
import kerebus.kerepic.bitmap.BitmapDecoder
import org.apache.http.impl.client.DefaultHttpClient

/**
 * Fetches pic at given url from either fileCache or the interwebs and sets it into the given imageView
 */
class FetchPictureTask(val memoryCache: MemoryCache,
                       val fileCache: FileCache,
                       val imageView: ImageView) extends KereAsyncTask[String, Void, Bitmap] {

  val imageViewRef = new WeakReference(imageView)

  var urlStr: String = _

  def doInBackground(urlStr : String): Bitmap = {
    this.urlStr = urlStr

    val fileBackedBitmap = findFromFileCache(urlStr)

    if (fileBackedBitmap != null) {
      memoryCache.put(urlStr, fileBackedBitmap)
      return fileBackedBitmap
    }

    val bitmap = findFromInterweb(urlStr)

    if (bitmap != null)
      memoryCache.put(urlStr, bitmap)

    return bitmap
  }

  private def findFromFileCache(urlStr: String): Bitmap = decodeFile(fileCache.getFile(urlStr))

  private def findFromInterweb(urlStr: String): Bitmap = {
    val url = new URL(urlStr)

    val conn: HttpURLConnection = url.openConnection().asInstanceOf[HttpURLConnection]
    conn.setConnectTimeout(30000)
    conn.setReadTimeout(30000)
    conn.setInstanceFollowRedirects(true)

    try {
      conn.connect()
      val inputStream = conn.getInputStream
      val file = fileCache.getFile(urlStr)
      val outputStream = new FileOutputStream(file)
      copyStream(inputStream, outputStream)
      outputStream.close()

      return decodeFile(file)
    } catch {
      case e: IOException => Log.w("KerePic", "Unable to fetch from " + urlStr)
    }

    return null
  }

  private def decodeFile(file: File): Bitmap = try
    BitmapDecoder.decode(file)
  catch {
    case e: FileNotFoundException => return null
  }

  override def onPostExecute(result:Bitmap) {
    if (isCancelled)
      return

    if (imageViewRef != null && result != null) {
      val imageView = imageViewRef.get()

      if (imageView != null)
        imageView.setImageBitmap(result)

    }

  }

  private def copyStream(is:InputStream, os:OutputStream) {
    val bufferSize = 1024

    val bytes = new Array[Byte](bufferSize)
    var count = is.read(bytes, 0, bufferSize)

    while (count != -1) {
      os.write(bytes, 0, count)
      count = is.read(bytes, 0, bufferSize)
    }

  }
}
