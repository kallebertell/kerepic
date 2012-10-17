package kerebus.kerepic.picsource

import ffffound.FfffoundPictureSource
import kerebus.kerepic.model.PictureMetadata

trait PictureSource {

  def fetchPictures: Seq[PictureMetadata]

}

object PictureSourceFactory {
  def forFfffound: PictureSource = new FfffoundPictureSource
}
