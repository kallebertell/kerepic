package kerebus.kerepic.cache

import android.graphics.Bitmap
import java.util
import android.util.Log

class MemoryCache {

  val TAG = "memoryCache"
  val cache = new util.LinkedHashMap[String, Bitmap](10, 1.5f, true)
  val sizeLimitInBytes = Runtime.getRuntime.maxMemory() / 4

  var currentSizeInBytes = 0

  def get(id: String): Bitmap = cache.get(id)

  def contains(id: String): Boolean = cache.containsKey(id)

  def put(id: String, bitmap: Bitmap) {
    Log.i("KerePic", "Putting into memory cache: "+id+" = " + bitmap)

    if (cache.containsKey(id))
        currentSizeInBytes -= sizeInBytes(cache.get(id))

    cache.put(id, bitmap)

    currentSizeInBytes += sizeInBytes(bitmap)
    evictFromCache
  }

  def evictFromCache: Unit = {
    if (currentSizeInBytes <= sizeLimitInBytes)
       return

    val iterator = cache.entrySet().iterator()

    while (iterator.hasNext) {
      val entry = iterator.next()
      currentSizeInBytes -= sizeInBytes(entry.getValue)
      iterator.remove()

      if (currentSizeInBytes <= sizeLimitInBytes)
        return
    }
  }

  def clear {
    cache.clear()
    currentSizeInBytes = 0
  }

  private def sizeInBytes(bitmap: Bitmap): Int = {
    return bitmap.getRowBytes * bitmap.getHeight
  }
}
