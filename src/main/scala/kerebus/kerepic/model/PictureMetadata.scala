package kerebus.kerepic.model

class PictureMetadata(val srcUrl: String, val desc: String) {

  override def toString = desc + " " + srcUrl;
}
