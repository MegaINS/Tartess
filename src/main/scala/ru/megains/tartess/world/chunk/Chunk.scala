package ru.megains.tartess.world.chunk


import ru.megains.tartess.block.data.{BlockDirection, BlockPos, BlockState}
import ru.megains.tartess.register.Blocks
import ru.megains.tartess.utils.{RayTraceResult, Vec3f}
import ru.megains.tartess.world.World
import ru.megains.tech.block.blockdata.BlockSidePos

import scala.collection.mutable
import scala.language.postfixOps



class Chunk(val position: ChunkPosition,val world: World) {





    var blockStorage = new BlockStorage(position)


    def getBlocks:mutable.HashSet[BlockState] ={
        blockStorage.getBlocks
    }

    def getBlock(pos: BlockPos):BlockState = {
        blockStorage.get(pos.x & 255,pos.y & 255,pos.z & 255)
    }

    def setBlock(blockState: BlockState):Boolean = {
        val pos = blockState.pos
        val block = blockState.block
        val blockStatePrevious =  getBlock(pos)

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

            if(x <= position.maxXP && y <= position.maxYP && z <= position.maxZP){
                blockStorage.setBlock(x & 255,y & 255,z & 255,blockState)
            }else{
                world.getChunk(x,y,z).blockStorage.setBlock(x & 255,y & 255,z & 255,blockState)
            }
        }
        true
    }
    def isOpaqueCube(pos: BlockPos,blockDirection: BlockDirection): Boolean = {
        val blockPos = new BlockPos(pos.x+blockDirection.x*16,pos.y+blockDirection.y*16,pos.z+blockDirection.z*16)
        if(world.getBlock(blockPos).block == Blocks.dirt) true
        else false
    }



    def isOpaqueCube(pos: BlockSidePos): Boolean = {


        for(x<-pos.minX to pos.maxX;
            y<-pos.minY to pos.maxY;
            z<-pos.minZ to pos.maxZ){

            val blockX = x & 255
            val blockY = y & 255
            val blockZ = z & 255
            if(x <= position.maxXP && y<=position.maxYP &&z<=position.maxZP){
                if(!blockStorage.get(blockX,blockY,blockZ).block.isOpaqueCube)
                    return false
            }else{
                if(!world.getChunk(x>>8,y>>8,z>>8).blockStorage.get(blockX,blockY,blockZ).block.isOpaqueCube)
                    return false
            }
        }
        true
    }

    def collisionRayTrace(blockpos: BlockPos, vec31: Vec3f, vec32: Vec3f):RayTraceResult = {
        blockStorage.collisionRayTrace(world, blockpos, vec31, vec32)
    }
}

object Chunk{

    val CHUNK_SIZE = 256
    def getIndex(x: Long, y: Long, z: Long): Long = (x & 16777215l) << 40 | (z & 16777215l) << 16 | (y & 65535L)

}
