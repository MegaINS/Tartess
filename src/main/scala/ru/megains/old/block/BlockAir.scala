package ru.megains.old.block

import ru.megains.old.blockdata.{BlockSize, BlockWorldPos}
import ru.megains.tartess.renderer.texture.TTextureRegister


class BlockAir(name:String) extends Block(name){


  override def registerTexture(textureRegister: TTextureRegister){}

  override def getSelectedBoundingBox(BlockWorldPos: BlockWorldPos/*,offset:MultiBlockPos*/) = BlockSize.NULL_AABB

  override def isOpaqueCube = false

  override def isAir: Boolean = true
}
