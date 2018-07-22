package ru.megains.tartess.block


import ru.megains.tartess.physics.{AABB, AABBs, BoundingBox, BoundingBoxes}

import scala.collection.mutable

class BlockGlass(name:String) extends Block(name){

    override val blockBodies: AABBs  = new AABBs(mutable.HashSet(new AABB(16)))
    override val boundingBoxes: BoundingBoxes = new BoundingBoxes(mutable.HashSet(  new BoundingBox(16)))
    override def isOpaqueCube: Boolean = false


}
