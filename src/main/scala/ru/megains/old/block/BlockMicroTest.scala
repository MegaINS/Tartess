package ru.megains.old.block

import ru.megains.old.blockdata.{BlockSize, BlockWorldPos}
import ru.megains.old.physics.BlockAxisAlignedBB


class BlockMicroTest(name:String,val size:Int) extends Block(name){





    lazy val boundingBox = Array(new BlockAxisAlignedBB(BlockSize.Zero,BlockSize.Zero,BlockSize.Zero,
                                                               BlockSize.FourSixteenth,BlockSize.FourSixteenth,BlockSize.FourSixteenth),
        new BlockAxisAlignedBB(BlockSize.Zero,BlockSize.Zero,BlockSize.Zero,
            BlockSize.EightSixteenth,BlockSize.EightSixteenth,BlockSize.EightSixteenth),
        new BlockAxisAlignedBB(BlockSize.Zero,BlockSize.Zero,BlockSize.Zero,
            BlockSize.TwelveSixteenth,BlockSize.TwelveSixteenth,BlockSize.TwelveSixteenth)
    )


    override def getPhysicsBody:BlockAxisAlignedBB = boundingBox(size)

    override def getSelectedBoundingBox ( blockPos:BlockWorldPos/*,offset:MultiBlockPos*/) = boundingBox(size)//.sum(offset.floatX,offset.floatY,offset.floatZ)

    override def isOpaqueCube: Boolean = false

    override def isFullBlock: Boolean = false

}
