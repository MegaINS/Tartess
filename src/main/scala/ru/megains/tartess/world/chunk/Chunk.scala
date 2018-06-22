package ru.megains.tartess.world.chunk

import ru.megains.tartess.block.data.{BlockPos, BlockState}
import ru.megains.tartess.register.Blocks
import ru.megains.tartess.world.World

import scala.collection.mutable
import scala.language.postfixOps



class Chunk(val position: ChunkPosition,val world: World) {





    var blockStorage = new BlockStorage(position)


    def getBlocks:mutable.HashSet[BlockState] ={
        blockStorage.getBlocks
    }

    def getBlock(pos: BlockPos):BlockState = {
        blockStorage.get(pos.x & 127,pos.y & 127,pos.z & 127)
    }

    def setBlock(blockState: BlockState):Boolean = {
        val pos = blockState.pos
        val block = blockState.block
        val blockStatePrevious =  getBlock(pos)

        //        val blockX = pos.x & 63
        //        val blockY = pos.y & 63
        //        val blockZ = pos.z & 63


        //TODO Доделать
        //        if((pos.isBase && blockState.block.blockSize == Block.baseBlockSize)||
        //                (pos.isBase && blockState.block == Blocks.air && getBlock(blockState.pos).block.blockSize == Block.baseBlockSize)){
        //            blockStorage.setDefaultBlock(blockX,blockY,blockZ,blockState)
        //        }else{


        val aabb = if(block == Blocks.air){
            blockStatePrevious.getBoundingBox
        }else{
            blockState.getBoundingBox
        }

        val minX = aabb.minX toInt
        val minY = aabb.minY toInt
        val minZ = aabb.minZ toInt
        val maxX = aabb.maxX toInt
        val maxY = aabb.maxY toInt
        val maxZ = aabb.maxZ toInt

        for(x <- minX until maxX;
            y <- minY until maxY;
            z <- minZ until maxZ){

            if(x<=position.maxX && y<=position.maxY &&z<=position.maxZ){
                blockStorage.setBlock(x &127,y & 127,z & 127,blockState)
            }else{
                world.getChunk(x,y,z).blockStorage.setBlock(x & 127,y & 127,z & 127,blockState)
            }
        }
        true
    }
}

object Chunk{

    val CHUNK_SIZE = 256
    def getIndex(x: Long, y: Long, z: Long): Long = (x & 16777215l) << 40 | (z & 16777215l) << 16 | (y & 65535L)

}
