package kerebus.kerepic.ui

import android.widget.{ImageView, TextView, ArrayAdapter}
import kerebus.kerepic.model.PictureMetadata
import android.content.Context
import android.view.{LayoutInflater, ViewGroup, View}
import kerebus.kerepic.{TR, R}
import android.graphics.{BitmapFactory, Bitmap}

class PictureAdapter(context: Context, viewId: Int) extends ArrayAdapter[PictureMetadata](context, viewId) {

  val placeholder = BitmapFactory.decodeResource(getContext.getResources, R.drawable.placeholder)
  val downloader = new PictureDownloader(context)
  val inflater = getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE).asInstanceOf[LayoutInflater]

  override def getView(position: Int, convertView: View, parent: ViewGroup): View = {
    val row: View = ensureRowView(convertView)
    val viewHolder = ensureCachingViewHolder(row)

    val textView: TextView = viewHolder.textView
    val imageView: ImageView = viewHolder.imageView

    val picMetadata = getItem(position)
    textView.setText(picMetadata.desc)

    downloader.downloadPicIntoImageView(picMetadata.srcUrl, placeholder, imageView)

    return row
  }

  private def ensureRowView(convertView: View): View = {
    if (convertView == null)
      return inflater.inflate(R.layout.row, null)

    return convertView
  }

  private def ensureCachingViewHolder(row: View): ViewHolder = {
    if (row.getTag() != null) {
      return row.getTag.asInstanceOf[ViewHolder]
    }

    val textView = row.findViewById(R.id.textView).asInstanceOf[TextView]
    val imageView = row.findViewById(R.id.imageView).asInstanceOf[ImageView]
    val holder = new ViewHolder(textView, imageView)

    row.setTag(holder)

    return holder
  }

  override def areAllItemsEnabled: Boolean = {
    return false
  }

  override def isEnabled(position: Int): Boolean = {
    return false
  }

  def addPics(pics: Seq[PictureMetadata]) {
    pics.foreach(p => add(p))
  }

  class ViewHolder(val textView:TextView, val imageView:ImageView)
}
