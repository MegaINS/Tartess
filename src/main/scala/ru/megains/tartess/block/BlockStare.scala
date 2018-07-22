package ru.megains.tartess.block
import ru.megains.tartess.physics.{AABB, AABBs, BoundingBox, BoundingBoxes}

import scala.collection.mutable

class BlockStare(name:String,x:Int) extends Block(name) {





    val blockBody = Array(
        new AABBs(mutable.HashSet(
            new AABB(0,0,0,16,8,16),
            new AABB(0,8,8,16,16,16)
        )),
        new AABBs(mutable.HashSet(
            new AABB(0,0,0,32,8,16),
            new AABB(0,8,8,32,16,16)
        ))
    )


    val boundingBox: Array[BoundingBoxes] = Array(
        new BoundingBoxes(mutable.HashSet(
            new BoundingBox(16,8,16),
            new BoundingBox(0,8,8,16,16,16),
        )),
        new BoundingBoxes(mutable.HashSet(
            new BoundingBox(32,8,16),
            new BoundingBox(0,8,8,32,16,16)
        ))
    )

    override val blockBodies: AABBs = blockBody(x)
    override val boundingBoxes: BoundingBoxes = boundingBox(x)


}
