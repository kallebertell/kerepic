package kerebus.kerepic.bitmap

import android.util.Log
import android.graphics.{Bitmap, BitmapFactory}
import java.io.{File, FileInputStream}

object BitmapDecoder {
  val REQUIRED_IMAGE_WIDTH = 300

  def decode(file: File): Bitmap = {
    val bounds = decodeBounds(file)

    val inSampleSizeOpts = new BitmapFactory.Options()
    inSampleSizeOpts.inSampleSize = BitmapDecoder.calculateScaleFactor(bounds._1, bounds._2)

    return BitmapFactory.decodeStream(new FileInputStream(file), null, inSampleSizeOpts)
  }

  def decodeBounds(file: File): (Int, Int) = {
    val decodeBoundsOpts = new BitmapFactory.Options()
    decodeBoundsOpts.inJustDecodeBounds = true

    BitmapFactory.decodeStream(new FileInputStream(file), null, decodeBoundsOpts)

    val width = decodeBoundsOpts.outWidth
    val height = decodeBoundsOpts.outHeight

    return (width, height)
  }

  def calculateScaleFactor(originalWidth: Int, originalHeight: Int) = originalWidth / REQUIRED_IMAGE_WIDTH

}
