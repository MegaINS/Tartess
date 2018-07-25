package ru.megains.tartess.common.block


import ru.megains.tartess.common.physics.{AABB, AABBs, BoundingBox, BoundingBoxes}

import scala.collection.mutable

class BlockWG(name:String) extends Block(name) {

    override val blockBodies: AABBs  = new AABBs(mutable.HashSet(new AABB(16)))
    override val boundingBoxes: BoundingBoxes = new BoundingBoxes(mutable.HashSet(  new BoundingBox(16)))
}
