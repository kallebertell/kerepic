package kerebus.kerepic.ui

import android.content.Context
import android.widget.ImageView
import android.graphics.Bitmap
import kerebus.kerepic.cache.{FileCache, MemoryCache}

/**
 * Knows how to download a picture into a target imageView asynchronously
 */
class PictureDownloader(context: Context) {

  val memoryCache = new MemoryCache
  val fileCache = new FileCache(context)

  def downloadPicIntoImageView(urlStr: String, placeholderBitmap:Bitmap, imageView:ImageView) {

    if (memoryCache.contains(urlStr)) {
      imageView.setImageBitmap(memoryCache.get(urlStr))
      return
    }

    if (!shouldStartNewTask(urlStr, imageView))
      return

    val task = new FetchPictureTask(memoryCache, fileCache, imageView)
    val asyncDrawable = new Placeholder(context.getResources, placeholderBitmap, task)
    imageView.setImageDrawable(asyncDrawable)
    task.execute(urlStr)
  }


  def shouldStartNewTask(urlStr: String, imageView: ImageView): Boolean = {
    val task: FetchPictureTask = getAsyncTask(imageView)

    if (task == null)
      return true

    if (task.urlStr != urlStr)
      return task.cancel(true)

    return false
  }

  def getAsyncTask(imageView: ImageView): FetchPictureTask = {
    if (imageView == null)
      return null

    if (imageView.getDrawable == null)
      return null

    imageView.getDrawable.getClass match {
      case drawable: Placeholder => drawable.getBitmapWorkerTask
      case _ => null
    }

  }
}
