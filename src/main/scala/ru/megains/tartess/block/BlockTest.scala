package ru.megains.tartess.block

import ru.megains.tartess.block.data.BlockState
import ru.megains.tartess.physics.AABB

class BlockTest(name:String,i:Int) extends Block(name){

    val blockBodys = Array(
        new AABB(0,0,0,15,15,15),
        new AABB(0,0,0,14,14,14),
        new AABB(0,0,0,13,13,13),
        new AABB(0,0,0,12,12,12),
        new AABB(0,0,0,11,11,11),
        new AABB(0,0,0,10,10,10),
        new AABB(0,0,0,9,9,9),
        new AABB(0,0,0,8,8,8),
        new AABB(0,0,0,7,7,7),
        new AABB(0,0,0,6,6,6),
        new AABB(0,0,0,5,5,5),
        new AABB(0,0,0,4,4,4),
        new AABB(0,0,0,3,3,3),
        new AABB(0,0,0,2,2,2),
        new AABB(0,0,0,1,1,1)
    )

    override val blockBody = blockBodys(i)

    override def getSelectedBoundingBox(blockState: BlockState): AABB = blockBody
}
