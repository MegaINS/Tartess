package ru.megains.tartess.common.block


import ru.megains.tartess.common.physics.{AABB, AABBs, BoundingBox, BoundingBoxes}

import scala.collection.mutable

class BlockTest(name:String,i:Int) extends Block(name){

    val blockBodys = Array(
        new AABBs( mutable.HashSet( new AABB(0,0,0,15,15,15))),
        new AABBs( mutable.HashSet( new AABB(0,0,0,14,14,14))),
        new AABBs( mutable.HashSet( new AABB(0,0,0,13,13,13))),
        new AABBs( mutable.HashSet( new AABB(0,0,0,12,12,12))),
        new AABBs( mutable.HashSet( new AABB(0,0,0,11,11,11))),
        new AABBs( mutable.HashSet( new AABB(0,0,0,10,10,10))),
        new AABBs( mutable.HashSet( new AABB(0,0,0,9,9,9))),
        new AABBs( mutable.HashSet( new AABB(0,0,0,8,8,8))),
        new AABBs( mutable.HashSet( new AABB(0,0,0,7,7,7))),
        new AABBs( mutable.HashSet( new AABB(0,0,0,6,6,6))),
        new AABBs( mutable.HashSet( new AABB(0,0,0,5,5,5))),
        new AABBs( mutable.HashSet( new AABB(0,0,0,4,4,4))),
        new AABBs( mutable.HashSet( new AABB(0,0,0,3,3,3))),
        new AABBs( mutable.HashSet( new AABB(0,0,0,2,2,2))),
        new AABBs( mutable.HashSet( new AABB(0,0,0,1,1,1))),

        new AABBs( mutable.HashSet( new AABB(0,0,0,160,16,160))),
        new AABBs( mutable.HashSet( new AABB(0,0,0,16,160,16))),
        new AABBs( mutable.HashSet( new AABB(0,0,0,4,32,4)))
    )
    val blockSizes = Array(
        new BoundingBoxes(mutable.HashSet( new BoundingBox(15))),
        new BoundingBoxes(mutable.HashSet( new BoundingBox(14))),
        new BoundingBoxes(mutable.HashSet( new BoundingBox(13))),
        new BoundingBoxes(mutable.HashSet( new BoundingBox(12))),
        new BoundingBoxes(mutable.HashSet( new BoundingBox(11))),
        new BoundingBoxes(mutable.HashSet( new BoundingBox(10))),
        new BoundingBoxes(mutable.HashSet( new BoundingBox(9))),
        new BoundingBoxes(mutable.HashSet( new BoundingBox(8))),
        new BoundingBoxes(mutable.HashSet( new BoundingBox(7))),
        new BoundingBoxes(mutable.HashSet( new BoundingBox(6))),
        new BoundingBoxes(mutable.HashSet( new BoundingBox(5))),
        new BoundingBoxes(mutable.HashSet( new BoundingBox(4))),
        new BoundingBoxes(mutable.HashSet( new BoundingBox(3))),
        new BoundingBoxes(mutable.HashSet( new BoundingBox(2))),
        new BoundingBoxes(mutable.HashSet( new BoundingBox(1))),

        new BoundingBoxes(mutable.HashSet( new BoundingBox(160,16,160))),
        new BoundingBoxes(mutable.HashSet( new BoundingBox(16,160,16))),
        new BoundingBoxes(mutable.HashSet(new BoundingBox(4,32,4)))
    )

    override val blockBodies = blockBodys(i)

    override val boundingBoxes: BoundingBoxes =blockSizes(i)

}
