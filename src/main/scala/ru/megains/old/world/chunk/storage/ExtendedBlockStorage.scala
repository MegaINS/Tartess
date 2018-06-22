package ru.megains.old.world.chunk.storage

import ru.megains.old.block.Block


class ExtendedBlockStorage {



    val data:BitArray = new BitArray(4096,16)

    def getIndex(x: Int, y: Int, z: Int): Int = x << 8 |  y << 4 | z

    def getBlockId(x:Int,y:Int,z:Int) =data.get(getIndex(x,y,z))
    def getBlock(x:Int,y:Int,z:Int) = Block.getBlockById(data.get(getIndex(x,y,z)))
    def setBlockId(x:Int,y:Int,z:Int,value:Int) = data.set(getIndex(x,y,z),value)



}
