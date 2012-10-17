import kerebus.kerepic.bitmap.BitmapDecoder
import kerebus.kerepic.picsource.ffffound.FfffoundFeedParser
import kerebus.kerepic.picsource.PictureSourceFactory
import org.scalatest.matchers.ShouldMatchers
import org.scalatest._

class FfffoundFeedParserSpecs extends FunSpec with ShouldMatchers {

 describe("feed parser") {
  it("should parse feed") {
    val lines = io.Source.fromInputStream(getClass.getResourceAsStream("ffffound_feed.xml")).mkString
    val pics = FfffoundFeedParser.parse(lines)
    assert(pics.size == 25)
  }

 }

}
