package ru.megains.tartess.world.chunk


import ru.megains.tartess.block.BlockContainer
import ru.megains.tartess.block.data._
import ru.megains.tartess.entity.Entity
import ru.megains.tartess.register.Blocks
import ru.megains.tartess.tileentity.{TileEntity, TileEntityContainer}
import ru.megains.tartess.utils.{RayTraceResult, Vec3f}
import ru.megains.tartess.world.World
import ru.megains.tartess.world.chunk.data.{BlockStorage, ChunkPosition}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps



class Chunk(val position: ChunkPosition,val world: World) {





    var blockStorage = new BlockStorage(this,position)
    var chunkEntityMap: ArrayBuffer[Entity] = new ArrayBuffer[Entity]()
    var chunkTileEntityMap = new mutable.HashMap[Long,TileEntity]()


    def isAirBlock(pos: BlockPos): Boolean = {
        getBlock(pos).block == Blocks.air
    }

    def isAirBlock(blockState: BlockState): Boolean = {
        var empty = true

        val aabb = blockState.getBoundingBox

        val minX = aabb.minX toInt
        val minY = aabb.minY toInt
        val minZ = aabb.minZ toInt
        val maxX = aabb.maxX toInt
        val maxY = aabb.maxY toInt
        val maxZ = aabb.maxZ toInt

        for(x<-minX until maxX;
            y<-minY until maxY;
            z<-minZ until maxZ){

            val blockX = x & 255
            val blockY = y & 255
            val blockZ = z & 255
            if(x <= position.maxXP && y<=position.maxYP &&z<=position.maxZP){
                if( blockStorage.get(blockX,blockY,blockZ)!= Blocks.air.blockState){
                    empty = false
                }
            }else{
                if(world.getChunk(x>>8,y>>8,z>>8).blockStorage.get(blockX,blockY,blockZ)!= Blocks.air.blockState) empty = false
            }
        }
        empty
    }

    def getTileEntity(pos: BlockPos): TileEntity = {
        var tileentity = chunkTileEntityMap.get( Chunk.getIndex(pos.x,pos.y,pos.z))
        tileentity.get
    }

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
        val blockCell = new mutable.HashSet[BlockCell]()

        for(x <- minX until maxX;
            y <- minY until maxY;
            z <- minZ until maxZ){
            if(x <= position.maxXP && y <= position.maxYP && z <= position.maxZP){
                blockCell += blockStorage.getBlockCell(x & 255,y & 255,z & 255)
            }else{
                blockCell +=   world.getChunk(x>>8,y>>8,z>>8).blockStorage.getBlockCell(x & 255,y & 255,z & 255)
            }
        }
        blockCell.foreach(_.setBlock(blockState))

        blockStatePrevious.block match {
            case _:BlockContainer =>
                world.removeTileEntity(blockStatePrevious.pos)
            case _ =>
        }

        block match {
            case container:TileEntityContainer =>
                val  tileEntity = container.createNewTileEntity(world,blockState)
                world.setTileEntity(pos, tileEntity)
            case _ =>
        }
        true
    }

    def removeTileEntity(pos: BlockPos): Unit = {
        chunkTileEntityMap -= Chunk.getIndex(pos.x,pos.y,pos.z)
    }

    def addTileEntity(tileEntityIn: TileEntity): Unit = {
        addTileEntity(tileEntityIn.pos,tileEntityIn)
        world.addTileEntity(tileEntityIn)
    }

    def addTileEntity(pos: BlockPos, tileEntityIn: TileEntity): Unit = {
        getBlock(pos).block match {
            case _:BlockContainer =>
                chunkTileEntityMap += Chunk.getIndex(pos.x,pos.y,pos.z) -> tileEntityIn
            case _ =>
        }
    }

    def isOpaqueCube(pos: BlockPos,blockDirection: BlockDirection): Boolean = {
        val blockPos = new BlockPos(pos.x+blockDirection.x*16,pos.y+blockDirection.y*16,pos.z+blockDirection.z*16)
        if(world.getBlock(blockPos).block == Blocks.dirt) true
        else false
    }

    def addEntity(entityIn: Entity): Unit = {
        chunkEntityMap += entityIn
        entityIn.chunkCoordX = position.x
        entityIn.chunkCoordY = position.y
        entityIn.chunkCoordZ = position.z
        world.addEntity(entityIn)
        entityIn.world = world
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
