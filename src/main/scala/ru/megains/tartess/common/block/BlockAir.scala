package ru.megains.tartess.common.block


import ru.megains.tartess.common.physics.{AABBs, BoundingBox, BoundingBoxes}
import ru.megains.tartess.client.renderer.texture.TTextureRegister

import scala.collection.mutable

object BlockAir extends Block("air") {

    override val blockBodies: AABBs  = new AABBs(mutable.HashSet())

    override val boundingBoxes: BoundingBoxes = new BoundingBoxes(mutable.HashSet())

    override def isOpaqueCube: Boolean = false

    override def registerTexture(textureRegister: TTextureRegister): Unit = {}


}
