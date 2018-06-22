package ru.megains.tartess.block

import ru.megains.tartess.block.data.BlockState
import ru.megains.tartess.physics.AABB

class BlockTest(name:String,i:Int) extends Block(name){
    val blockBodys = Array(
        new AABB(0,0,0,0.5,0.5,0.5),
        new AABB(0,0,0,0.25,0.25,0.25),
        new AABB(0,0,0,0.125,0.125,0.125)
    )

    override val blockBody = blockBodys(i)

    override def getSelectedBoundingBox(blockState: BlockState): AABB = blockBody
}
