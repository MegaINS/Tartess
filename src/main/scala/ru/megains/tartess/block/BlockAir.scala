package ru.megains.tartess.block


import ru.megains.tartess.physics.{AABB, BoundingBox}
import ru.megains.tartess.renderer.texture.TTextureRegister

object BlockAir extends Block("air") {

    override val blockBody: AABB  = new AABB()

    override val boundingBox: BoundingBox = new BoundingBox(0)

    override def isOpaqueCube: Boolean = false

    override def registerTexture(textureRegister: TTextureRegister): Unit = {}


}
