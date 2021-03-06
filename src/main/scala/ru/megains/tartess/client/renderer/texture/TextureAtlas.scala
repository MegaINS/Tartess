package ru.megains.tartess.client.renderer.texture

import java.io.InputStream
import java.nio.ByteBuffer

import de.matthiasmann.twl.utils.PNGDecoder
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11._



class TextureAtlas(val name:String) {

  var averageU: Float = _
  var minV: Float = _
  var minU: Float = _
  var maxU: Float = _
  var averageV: Float = _
  var width: Int = _
  var height: Int = _
  var maxV: Float = _

  val is: InputStream = getClass.getResourceAsStream("/textures/"+ name+".png")
  var byteByf: ByteBuffer = _
  var startX:Int = 0
  var startY:Int =0

  def isMissingTexture: Boolean = is == null


  def loadTexture(): Unit = {

      val png = new PNGDecoder(is)
      width = png.getWidth
      height = png.getHeight
      byteByf = ByteBuffer.allocateDirect(width*height*4)
      png.decode(byteByf,width*4,PNGDecoder.Format.RGBA)
      byteByf.flip()
  }
  def updateTexture(widthAll:Float,heightAll:Float): Unit = {
    minU = startX/widthAll
    maxU =  (startX+width)/widthAll
    minV = startY/heightAll
    maxV = (startY+height)/heightAll
    averageU = (minU+maxU)/2
    averageV = (minV+maxV)/2

    glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, startX, startY, width, height, GL_RGBA, GL_UNSIGNED_BYTE, byteByf)
  }


}
