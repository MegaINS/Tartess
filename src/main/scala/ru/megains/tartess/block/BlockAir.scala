package ru.megains.tartess.block

import ru.megains.tartess.block.data.BlockSize
import ru.megains.tartess.physics.{AABB, BoundingBox}
import ru.megains.tartess.renderer.texture.TTextureRegister

object BlockAir extends Block("air") {

    override val blockBody: AABB  = new AABB()
    override val blockSize: BlockSize  = new BlockSize()
    override val boundingBox: BoundingBox = new BoundingBox()

    override def isOpaqueCube: Boolean = false

    override def registerTexture(textureRegister: TTextureRegister): Unit = {}


}
