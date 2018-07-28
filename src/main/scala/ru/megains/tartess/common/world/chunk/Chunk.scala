package ru.megains.tartess.common.world.chunk


import ru.megains.tartess.common.block.data._
import ru.megains.tartess.common.block.{BlockContainer, BlockWG}
import ru.megains.tartess.common.entity.Entity
import ru.megains.tartess.common.physics.BoundingBoxes
import ru.megains.tartess.common.register.Blocks
import ru.megains.tartess.common.tileentity.{TileEntity, TileEntityContainer}
import ru.megains.tartess.common.utils.{RayTraceResult, Vec3f}
import ru.megains.tartess.common.world.World
import ru.megains.tartess.common.world.chunk.data.{BlockStorage, ChunkPosition}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps



class Chunk(val position: ChunkPosition,val world: World) {



    var isPopulated: Boolean = true
    var isSaved = true
    var blockStorage = new BlockStorage(position)
    var chunkEntityMap: ArrayBuffer[Entity] = new ArrayBuffer[Entity]()
    var chunkTileEntityMap = new mutable.HashMap[Long,TileEntity]()
    def needsSaving(p_76601_1: Boolean): Boolean = {
        isSaved
    }
    def isVoid: Boolean = {
        val blockData = blockStorage.blocksId
        val airId = Blocks.getIdByBlock(Blocks.air)
        for (i <- 0 until 4096) {
            if (blockData(i) != airId) return false
        }
        true
    }
    def isAirBlock(pos: BlockPos): Boolean = {
        getBlock(pos).block == Blocks.air
    }

    def isAirBlock(x:Int,y:Int,z:Int): Boolean={
        getBlock(x,y,z).block == Blocks.air
    }

    def isAirBlock(blockState: BlockState): Boolean = {
        var empty = true
        //TODO

        val aabb:BoundingBoxes = blockState.getSelectedBoundingBox

        val minX:Int = aabb.minX
        val minY:Int = aabb.minY
        val minZ:Int = aabb.minZ
        val maxX:Int = aabb.maxX
        val maxY:Int = aabb.maxY
        val maxZ:Int = aabb.maxZ

        for(x<-minX until maxX;
            y<-minY until maxY;
            z<-minZ until maxZ){

            val blockX = x
            val blockY = y
            val blockZ = z
            if(x <= position.maxXP && y<=position.maxYP &&z<=position.maxZP){
                if( blockStorage.get(blockX,blockY,blockZ)!= Blocks.air.airState){
                    empty = false
                }
            }else{
                if(world.getChunk(x>>8,y>>8,z>>8).blockStorage.get(blockX,blockY,blockZ)!= Blocks.air.airState) empty = false
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
        blockStorage.get(pos.x ,pos.y ,pos.z )
    }
    def getBlock(x:Int,y:Int,z:Int):BlockState = {
        blockStorage.get(x ,y ,z )
    }
    def setBlock(blockState: BlockState):Boolean = {
        //TODO
        val pos = blockState.pos
        val block = blockState.block
        val blockStatePrevious =  getBlock(pos)


        val aabb:BoundingBoxes = block match {
            case Blocks.air =>   blockStatePrevious.getSelectedBoundingBox
            case  _=>  blockState.getSelectedBoundingBox
        }


        val minX:Int = aabb.minX
        val minY:Int = aabb.minY
        val minZ:Int = aabb.minZ
        val maxX:Int = aabb.maxX
        val maxY:Int = aabb.maxY
        val maxZ:Int = aabb.maxZ





        val blockCell = new mutable.HashSet[BlockCell]()

        for(x <- minX until maxX;
            y <- minY until maxY;
            z <- minZ until maxZ){
            if(x <= position.maxXP && y <= position.maxYP && z <= position.maxZP){
                blockCell += blockStorage.getBlockCell(x & 255,y & 255,z & 255)
            }else{
                blockCell += world.getChunk(x>>8,y>>8,z>>8).blockStorage.getBlockCell(x & 255,y & 255,z & 255)
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
        //TODO
        if(pos.x % 16 == 0 && pos.y % 16 == 0 && pos.z % 16==0){
            val blockState = world.getBlock(new BlockPos(pos.x+blockDirection.x*16,pos.y+blockDirection.y*16,pos.z+blockDirection.z*16))
            if(blockState.pos.x % 16 == 0 && blockState.pos.y % 16 == 0 && blockState.pos.z % 16 == 0){
                blockState.block.isInstanceOf[BlockWG]
            } else false
        } else false
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
        //TODO
        for(x<-pos.minX to pos.maxX;
            y<-pos.minY to pos.maxY;
            z<-pos.minZ to pos.maxZ){

            val blockX = x
            val blockY = y
            val blockZ = z
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
