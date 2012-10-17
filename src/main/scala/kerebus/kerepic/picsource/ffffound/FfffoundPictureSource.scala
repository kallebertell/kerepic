package kerebus.kerepic.picsource.ffffound

import kerebus.kerepic.picsource.PictureSource
import kerebus.kerepic.model.PictureMetadata
import org.apache.http.impl.client.DefaultHttpClient
import java.net.URI
import org.apache.http.client.methods.HttpGet
import io.Source

/**
 * PictureSource which fetches from pic data from ffffound.com:s rss feed
 */
class FfffoundPictureSource extends PictureSource {

  val rssUrl = "http://feeds.feedburner.com/ffffound/everyone";

  def fetchPictures: Seq[PictureMetadata] = {
    val client = new DefaultHttpClient
    val request = new HttpGet
    request.setURI(new URI(rssUrl))
    val response = client.execute(request)
    val inputStream = response.getEntity.getContent

    return FfffoundFeedParser.parse( Source.fromInputStream(inputStream).mkString )
  }

}
