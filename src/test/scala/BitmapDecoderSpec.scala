import kerebus.kerepic.bitmap.BitmapDecoder
import org.scalatest.fixture.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSpec
import org.scalatest._

class BitmapDecoderSpec extends FunSpec with ShouldMatchers {

 describe("bitmap decoder") {
  it("should calc scale factor") {
    val scaleFactor = BitmapDecoder.calculateScaleFactor(300, 300)
    assert(scaleFactor == 1)
  }

 }

}
