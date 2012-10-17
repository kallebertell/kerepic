package kerebus.kerepic

import _root_.android.app.Activity
import android.os.{StrictMode, Bundle}
import android.view._
import android.widget.{TextView, ArrayAdapter}
import android.content.Context
import model.PictureMetadata
import picsource.PictureSourceFactory
import ui.PictureAdapter
import scala.collection.JavaConversions._

class MainActivity extends Activity with TypedActivity {

  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    enableStrictMode

    //Remove title bar
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    //Remove notification bar
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.main)

    val listView = findView(TR.listView)

    val adapter = new PictureAdapter(this, R.layout.row)

    val pics = PictureSourceFactory.forFfffound.fetchPictures
    adapter.addPics(pics)

//    adapter.add(newPic("http://24.media.tumblr.com/tumblr_m7wmzw5Jd71qgcmtvo1_1280.png"))
//    adapter.add(newPic("http://30.media.tumblr.com/tumblr_lu97ifGOk81qei7a7o1_500.jpg"))
//    adapter.add(newPic("http://30.media.tumblr.com/tumblr_m0rfvpObGZ1r8h0lzo1_500.jpg"))
//    adapter.add(newPic("http://28.media.tumblr.com/tumblr_m2w8q9fN121qaiyl9o1_500.jpg"))
//    adapter.add(newPic("http://deadfix.com/wp-content/uploads/2012/04/floors.jpg"))
//    adapter.add(newPic("http://26.media.tumblr.com/tumblr_lqc7y0mNkr1qzzhs8o1_500.jpg"))
//    adapter.add(newPic("http://24.media.tumblr.com/tumblr_lyq6f9AtJL1qz6f9yo1_500.jpg"))
//    adapter.add(newPic("http://30.media.tumblr.com/tumblr_lzeq9iyRij1qz6f9yo1_500.jpg"))
//
    listView.setAdapter(adapter)
  }

  def newPic(url: String) = new PictureMetadata(url, "foo")


  def enableStrictMode = {
    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
      .detectDiskReads()
      .detectDiskWrites()
      .detectNetwork()
      .penaltyLog()
      .build())

    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
      .detectLeakedSqlLiteObjects()
      //.detectLeakedClosableObjects()
      .penaltyLog()
      .penaltyDeath()
      .build())
  }
}
