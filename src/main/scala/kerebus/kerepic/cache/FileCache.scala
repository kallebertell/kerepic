package kerebus.kerepic.cache

import java.io.File
import android.content.Context
import android.os.Environment

class FileCache(context:Context) {
  var cacheDir: File = _

  if (Environment.getExternalStorageState.equals(Environment.MEDIA_MOUNTED))
    cacheDir = new File(Environment.getExternalStorageDirectory, "LazyList")
  else
    cacheDir = context.getCacheDir

  if (!cacheDir.exists())
    cacheDir.mkdirs()

  def getFile(url: String): File = new File(cacheDir, url.hashCode.toString)

  def clear() {
    val files: Array[File] = cacheDir.listFiles()
    if (files == null)
      return

    files foreach (f => f.delete())
  }
}
