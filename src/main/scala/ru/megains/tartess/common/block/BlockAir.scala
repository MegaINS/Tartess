package ru.megains.tartess.common.block


import ru.megains.tartess.common.physics.{AABBs, BoundingBoxes}

import scala.collection.mutable

object BlockAir extends Block("air") {

    override val blockBodies: AABBs  = new AABBs(mutable.HashSet())

    override val boundingBoxes: BoundingBoxes = new BoundingBoxes(mutable.HashSet())

    override def isOpaqueCube: Boolean = false



}
