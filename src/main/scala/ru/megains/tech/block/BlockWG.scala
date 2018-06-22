package ru.megains.tech.block


import ru.megains.tech.block.blockdata.BlockSize
import ru.megains.tech.physics.AxisAlignedBB

class BlockWG(name: String) extends Block(name){


    override val blockBody:AxisAlignedBB =  new AxisAlignedBB(0,0,0,4,4,4)
    override val blockSize: BlockSize = Block.baseBlockSize
    override def getSelectedBoundingBox(blockState: BlockState): AxisAlignedBB = blockBody.rotate(blockState.blockDirection)
}
