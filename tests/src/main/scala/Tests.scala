package kerebus.kerepic.tests

import junit.framework.Assert._
import _root_.android.test.AndroidTestCase
import _root_.android.test.ActivityInstrumentationTestCase2
import kerebus.kerepic.{TR, MainActivity}

class AndroidTests extends AndroidTestCase {
  def testPackageIsCorrect() {
    assertEquals("kerepic", getContext.getPackageName)
  }
}

class ActivityTests extends ActivityInstrumentationTestCase2(classOf[MainActivity]) {
   def testHelloWorldIsShown() {
      val activity = getActivity
      val textview = activity.findView(TR.textView)
      assertEquals(textview.getText, "hello, world!")
    }
}
