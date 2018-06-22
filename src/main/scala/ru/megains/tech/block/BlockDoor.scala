package ru.megains.tech.block

import ru.megains.tech.block.blockdata.BlockSize
import ru.megains.tech.physics.AxisAlignedBB

class BlockDoor(name:String) extends Block(name){

    override val blockBody: AxisAlignedBB = new AxisAlignedBB(0,0,0,1,8,4)
    override val blockSize: BlockSize = BlockSize(1,8,4)
}
