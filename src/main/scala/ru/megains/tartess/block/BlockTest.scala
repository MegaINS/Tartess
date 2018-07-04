package ru.megains.tartess.block

import ru.megains.tartess.block.data.BlockSize
import ru.megains.tartess.physics.{AABB, BoundingBox}

class BlockTest(name:String,i:Int) extends Block(name){

    val blockBodys = Array(
        new AABB(0,0,0,15/16f,15/16f,15/16f),
        new AABB(0,0,0,14/16f,14/16f,14/16f),
        new AABB(0,0,0,13/16f,13/16f,13/16f),
        new AABB(0,0,0,12/16f,12/16f,12/16f),
        new AABB(0,0,0,11/16f,11/16f,11/16f),
        new AABB(0,0,0,10/16f,10/16f,10/16f),
        new AABB(0,0,0,9/16f,9/16f,9/16f),
        new AABB(0,0,0,8/16f,8/16f,8/16f),
        new AABB(0,0,0,7/16f,7/16f,7/16f),
        new AABB(0,0,0,6/16f,6/16f,6/16f),
        new AABB(0,0,0,5/16f,5/16f,5/16f),
        new AABB(0,0,0,4/16f,4/16f,4/16f),
        new AABB(0,0,0,3/16f,3/16f,3/16f),
        new AABB(0,0,0,2/16f,2/16f,2/16f),
        new AABB(0,0,0,1/16f,1/16f,1/16f),

        new AABB(0,0,0,160/16f,16/16f,160/16f),
        new AABB(0,0,0,16/16f,160/16f,16/16f),
        new AABB(0,0,0,4/16f,32/16f,4/16f)

    )
    val blockSizes = Array(
        new BlockSize(15),
        new BlockSize(14),
        new BlockSize(13),
        new BlockSize(12),
        new BlockSize(11),
        new BlockSize(10),
        new BlockSize(9),
        new BlockSize(8),
        new BlockSize(7),
        new BlockSize(6),
        new BlockSize(5),
        new BlockSize(4),
        new BlockSize(3),
        new BlockSize(2),
        new BlockSize(1),

        new BlockSize(160,16,160),
        new BlockSize(16,160,16),
        new BlockSize(4,32,4)

    )
    override val blockBody = blockBodys(i)
    override val blockSize = blockSizes(i)
    override val boundingBox: BoundingBox = new BoundingBox(blockSize)

}
