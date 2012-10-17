package kerebus.kerepic.picsource.ffffound

import kerebus.kerepic.model.PictureMetadata
import scala.xml.pull.XMLEventReader
import scala.xml.XML

object FfffoundFeedParser {

  def parse(xmlStr:String): Seq[PictureMetadata] = {
    val xml = XML.loadString(xmlStr)

    val channelNode = xml.child.find(n => n.label.equals("channel")).get

    val pics = new collection.mutable.MutableList[PictureMetadata]

    channelNode.child
      .filter(n => n.label.equals("item"))
      .foreach(item => {
        val title = item.child.find(n => n.label.equals("title")).get.child.head.toString
        val content = item.child.find(n => n.label.equals("content")).get
        val url = content.attribute("url").get.toString
        val pic = new PictureMetadata(url, title)
        pics += pic
    });

    return pics
  }
}
