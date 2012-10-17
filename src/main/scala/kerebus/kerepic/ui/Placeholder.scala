package kerebus.kerepic.ui

import android.graphics.drawable.BitmapDrawable
import android.content.res.Resources
import android.graphics.Bitmap
import java.lang.ref.WeakReference

/**
 * A placeholder drawable which has a reference to the async task downloading its content
 */
class Placeholder(val resources: Resources,
                    val bitmap: Bitmap,
                    val task: FetchPictureTask) extends BitmapDrawable(resources, bitmap) {

  val taskRef = new WeakReference(task)

  val getBitmapWorkerTask = {
    taskRef.get()
  }
}
