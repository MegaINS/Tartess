package ru.megains.tartess.block

import ru.megains.tartess.block.data.BlockState
import ru.megains.tartess.physics.AABB

class BlockTest(name:String,i:Int) extends Block(name){
    val blockBodys = Array(
        new AABB(0,0,0,4,4,4),
        new AABB(0,0,0,2,2,2),
        new AABB(0,0,0,1,1,1)
    )

    override val blockBody = blockBodys(i)

    override def getSelectedBoundingBox(blockState: BlockState): AABB = blockBody
}
