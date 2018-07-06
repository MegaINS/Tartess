package ru.megains.tartess.world



import ru.megains.tartess.block.data.{BlockDirection, BlockPos, BlockSidePos, BlockState}
import ru.megains.tartess.entity.Entity
import ru.megains.tartess.register.Blocks
import ru.megains.tartess.renderer.world.WorldRenderer
import ru.megains.tartess.tileentity.TileEntity
import ru.megains.tartess.utils.{MathHelper, RayTraceResult, Vec3f}
import ru.megains.tartess.world.chunk.Chunk
import ru.megains.tartess.world.chunk.data.ChunkProvider

import scala.collection.mutable.ArrayBuffer



class World {

    val length: Int = 100000
    val width: Int = 100000
    val height: Int = 1000

    var worldRenderer:WorldRenderer = _
    val chunkProvider:ChunkProvider = new ChunkProvider(this)
    val entities: ArrayBuffer[Entity] = new ArrayBuffer[Entity]()
    val tickableTileEntities: ArrayBuffer[TileEntity] = new ArrayBuffer[TileEntity]()

    def getChunk(x: Int, y: Int, z: Int): Chunk = {
        if((x<<8 <= length && x<<8 >= -length) && (z<<8 <= width  && z<<8 >= -width) && (y<<8 <= height && y<<8 >= -height)){
            chunkProvider.provideChunk(x,y,z)
        } else{
            ChunkProvider.voidChunk
        }

    }

    def getChunk(blockPos: BlockPos): Chunk = {
        if((blockPos.x <= length && blockPos.x >= -length) && (blockPos.z <= width  && blockPos.z >= -width) && (blockPos.y <= height && blockPos.y >= -height)){
            chunkProvider.provideChunk(blockPos.x>>8,blockPos.y>>8,blockPos.z>>8)
        } else{
            ChunkProvider.voidChunk
        }

    }

    def getBlock(blockPos: BlockPos): BlockState = {
        if (!blockPos.isValid(this)) {
            return Blocks.air.airState
        }
        val chunk = getChunk(blockPos)
        chunk.getBlock(blockPos)
    }

    def removeTileEntity(blockPos: BlockPos): Unit = {


        val chunk = getChunk(blockPos)
        if (chunk != null) {
            tickableTileEntities -= chunk.getTileEntity(blockPos)
            chunk.removeTileEntity(blockPos)

        }

    }

    def setTileEntity(blockPos: BlockPos, tileEntityIn: TileEntity): Unit = {

        val chunk = getChunk(blockPos)
        if (chunk != null) chunk.addTileEntity(blockPos, tileEntityIn)
        addTileEntity(tileEntityIn)

    }

    def isAirBlock(blockPos: BlockPos): Boolean = if (blockPos.isValid(this)) getChunk(blockPos).isAirBlock(blockPos) else true

    def isAirBlock(blockState: BlockState): Boolean = {
        if (blockState.pos.isValid(this)) getChunk(blockState.pos).isAirBlock(blockState) else true
    }

    def addTileEntity(tile: TileEntity): Unit = {
        tickableTileEntities += tile
    }

    def setBlock(blockState: BlockState): Boolean ={
        if (!blockState.pos.isValid(this)) {
            return false
        }
        worldRenderer.reRender(blockState.pos)
        val chunk = getChunk(blockState.pos)
        chunk.setBlock(blockState)
    }

    def getTileEntity(blockPos: BlockPos): TileEntity ={
        if (!blockPos.isValid(this)){
            null
        } else {
            getChunk(blockPos).getTileEntity(blockPos)
        }
    }

    def spawnEntityInWorld(entity: Entity): Unit = {
        val chunk = getChunk( entity.posX toInt,entity.posY toInt,entity.posZ toInt)
        if (chunk != null){
            chunk.addEntity(entity)
        }
    }

    def update() {
        entities.foreach(_.update())
        tickableTileEntities.foreach(_.update(this))
    }

    def addEntity(entityIn: Entity): Unit = {
        entities += entityIn
    }

    def removeEntity(entityIn: Entity): Unit = {
        entities -= entityIn
    }

    def isOpaqueCube(pos: BlockSidePos): Boolean  = {
        getChunk(pos.minX>>8,pos.minY>>8,pos.minZ>>8).isOpaqueCube(pos)
    }

    def isOpaqueCube(pos: BlockPos, blockDirection: BlockDirection): Boolean = {
        getChunk(pos).isOpaqueCube(pos,blockDirection)
    }

    def setAirBlock(pos: BlockPos):Boolean ={
        setBlock(new BlockState(Blocks.air,pos))
    }

    def rayTraceBlocks(vec1: Vec3f, vec32: Vec3f, stopOnLiquid: Boolean, ignoreBlockWithoutBoundingBox: Boolean, returnLastUncollidableBlock: Boolean): RayTraceResult = {

        var vec31: Vec3f = vec1
        val i: Int = MathHelper.floor_double(vec32.x)
        val j: Int = MathHelper.floor_double(vec32.y)
        val k: Int = MathHelper.floor_double(vec32.z)
        var l: Int = MathHelper.floor_double(vec31.x)
        var i1: Int = MathHelper.floor_double(vec31.y)
        var j1: Int = MathHelper.floor_double(vec31.z)
        var blockpos: BlockPos = null
        val raytraceresult2: RayTraceResult = null
        var chunk:Chunk = null

        var xBS,yBS,zBS = 0
        var k1:Int = 200
        while (k1 >=0) {
            k1-=1
            if (l == i && i1 == j && j1 == k) {
                return if (returnLastUncollidableBlock) raytraceresult2 else null
            }
            var flag2: Boolean = true
            var flag: Boolean = true
            var flag1: Boolean = true
            var d0: Double = 999.0D
            var d1: Double = 999.0D
            var d2: Double = 999.0D
            if (i > l) {
                d0 = l + 1.0D
            } else if (i < l) {
                d0 = l + 0.0D
            } else {
                flag2 = false
            }
            if (j > i1) {
                d1 = i1 + 1.0D
            } else if (j < i1) {
                d1 = i1 + 0.0D
            } else {
                flag = false
            }
            if (k > j1) {
                d2 = j1 + 1.0D
            } else if (k < j1) {
                d2 = j1 + 0.0D
            } else {
                flag1 = false
            }
            var d3: Double = 999.0D
            var d4: Double = 999.0D
            var d5: Double = 999.0D
            val d6: Double = vec32.x - vec31.x
            val d7: Double = vec32.y - vec31.y
            val d8: Double = vec32.z - vec31.z
            if (flag2) {
                d3 = (d0 - vec31.x) / d6
            }
            if (flag) {
                d4 = (d1 - vec31.y) / d7
            }
            if (flag1) {
                d5 = (d2 - vec31.z) / d8
            }
            if (d3 == -0.0D) {
                d3 = -1.0E-4f
            }
            if (d4 == -0.0D) {
                d4 = -1.0E-4f
            }
            if (d5 == -0.0D) {
                d5 = -1.0E-4f
            }
            var enumfacing: BlockDirection = null
            if (d3 < d4 && d3 < d5) {
                enumfacing = if (i > l) BlockDirection.WEST else BlockDirection.EAST
                vec31 = new Vec3f(d0 toFloat, vec31.y + d7 * d3 toFloat, vec31.z + d8 * d3 toFloat)
            } else if (d4 < d5) {
                enumfacing = if (j > i1) BlockDirection.DOWN else BlockDirection.UP
                vec31 = new Vec3f(vec31.x + d6 * d4 toFloat, d1 toFloat, vec31.z + d8 * d4 toFloat)
            } else {
                enumfacing = if (k > j1) BlockDirection.NORTH else BlockDirection.SOUTH
                vec31 = new Vec3f(vec31.x + d6 * d5 toFloat, vec31.y + d7 * d5 toFloat, d2 toFloat)
            }
            l = MathHelper.floor_double(vec31.x) - (if (enumfacing == BlockDirection.EAST) 1 else 0)
            i1 = MathHelper.floor_double(vec31.y) - (if (enumfacing == BlockDirection.UP) 1 else 0)
            j1 = MathHelper.floor_double(vec31.z) - (if (enumfacing == BlockDirection.SOUTH) 1 else 0)

            val l0 = (l>>4)<<4
            val i10 = (i1>>4)<<4
            val j110 = (j1>>4)<<4


            if(xBS != l0 || yBS != i10 || zBS != j110){
                xBS = l0
                yBS = i10
                zBS = j110
                blockpos = new BlockPos(xBS, yBS, zBS)
                chunk = getChunk(blockpos)


                val raytraceresult1: RayTraceResult = chunk.collisionRayTrace(blockpos,  vec31, vec32)
                if (raytraceresult1 != null) {
                    return raytraceresult1
                }
                val block1: BlockState = getBlock(blockpos)
                if (block1 != null && block1 != Blocks.air.airState) {
                    val raytraceresult1: RayTraceResult = block1.collisionRayTrace(this,  vec31, vec32)
                    if (raytraceresult1 != null) {
                        return raytraceresult1
                    }
                }
            }




        }
        null
    }

}
