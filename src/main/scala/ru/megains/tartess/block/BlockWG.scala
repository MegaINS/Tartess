package ru.megains.tartess.block

import ru.megains.tartess.block.data.BlockSize
import ru.megains.tartess.physics.{AABB, BoundingBox}

class BlockWG(name:String) extends Block(name) {

    override val blockBody:AABB = new AABB(1)
    override val blockSize: BlockSize = new BlockSize(16)
    override val boundingBox: BoundingBox = new BoundingBox(blockSize)
}
