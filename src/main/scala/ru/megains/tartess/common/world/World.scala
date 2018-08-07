package ru.megains.tartess.common.world



import ru.megains.tartess.client.renderer.world.WorldRenderer
import ru.megains.tartess.common.block.data.{BlockDirection, BlockPos, BlockSidePos, BlockState}
import ru.megains.tartess.common.entity.Entity
import ru.megains.tartess.common.physics.{AABB, AABBs}
import ru.megains.tartess.common.register.Blocks
import ru.megains.tartess.common.tileentity.TileEntity
import ru.megains.tartess.common.utils.{Logger, MathHelper, RayTraceResult, Vec3f}
import ru.megains.tartess.common.world.chunk.Chunk
import ru.megains.tartess.common.world.chunk.data.{ChunkLoader, ChunkPosition, ChunkProvider}
import ru.megains.tartess.server.world.WorldServer

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.collection.parallel.mutable.ParHashSet
import scala.language.postfixOps



class World(saveHandler:ISaveHandler) extends Logger[World]{

    val length: Int = 100000
    val width: Int = 100000
    val height: Int = 10000
    val heightMap: WorldHeightMap = new WorldHeightMap(0)
    var worldRenderer:WorldRenderer = _
    val chunkLoader: ChunkLoader = saveHandler.getChunkLoader
    val chunkProvider:IChunkProvider = new ChunkProvider(this,chunkLoader)
    val entities: ParHashSet[Entity] = new ParHashSet[Entity]()
    val tickableTileEntities: ArrayBuffer[TileEntity] = new ArrayBuffer[TileEntity]()
    val eventListeners: ArrayBuffer[IWorldEventListener] = ArrayBuffer[IWorldEventListener]()






    def getChunk(x: Int, y: Int, z: Int): Chunk = {
        if((x<<8 <= length && x<<8 >= -length) && (z<<8 <= width  && z<<8 >= -width) && (y<<8 <= height && y<<8 >= -height)){
            chunkProvider.provideChunk(x,y,z)
        } else{
            ChunkProvider.voidChunk
        }

    }

    def getChunk(pos: ChunkPosition): Chunk = {
        chunkProvider.provideChunk(pos.x,pos.y,pos.z)
    }

    def getChunk(blockPos: BlockPos): Chunk = {
        getChunkBlockPos(blockPos.x,blockPos.y,blockPos.z)
    }
    def getChunkBlockPos(x:Int,y:Int,z:Int): Chunk = {
        if((x <= length && x >= -length) && (z <= width  && z >= -width) && (y <= height && y >= -height)){
            chunkProvider.provideChunk(x>>8,y>>8,z>>8)
        } else{
            ChunkProvider.voidChunk
        }

    }
    def getBlock(blockPos: BlockPos): BlockState = {
        if(blockPos== null){
            blockPos.x
        }
        if (!blockPos.isValid(this)) {
            return Blocks.air.airState
        }
        val chunk = getChunk(blockPos)
        chunk.getBlock(blockPos)
    }

    def getBlock(x:Int,y:Int,z:Int): BlockState = {
//        if (!blockPos.isValid(this)) {
//            return Blocks.air.airState
//        }
        val chunk = getChunkBlockPos(x,y,z)
        chunk.getBlock(x,y,z)
    }

    def removeTileEntity(blockPos: BlockPos): Unit = {


        val chunk = getChunk(blockPos)
        if (chunk != null) {
            tickableTileEntities -= chunk.getTileEntity(blockPos)
            chunk.removeTileEntity(blockPos)

        }

    }

    def addBlocksInList(aabb: AABB): mutable.HashSet[AABBs] = {
        var x0: Int = Math.floor(aabb.minX )  toInt
        var y0: Int = Math.floor(aabb.minY)  toInt
        var z0: Int = Math.floor(aabb.minZ) toInt
        var x1: Int = Math.ceil(aabb.maxX)  toInt
        var y1: Int = Math.ceil(aabb.maxY)  toInt
        var z1: Int = Math.ceil(aabb.maxZ)  toInt


        if (x0 < -length) {
            x0 = -length
        }
        if (y0 < -height) {
            y0 = -height
        }
        if (z0 < -width) {
            z0 = -width
        }
        if (x1 > length) {
            x1 = length
        }
        if (y1 > height) {
            y1 = height
        }
        if (z1 > width) {
            z1 = width
        }

        val aabbs = mutable.HashSet[AABBs]()

        for (x <- x0 to x1; y <- y0 to y1; z <- z0 to z1) {


            if (!isAirBlock(x, y, z)) {
                aabbs += getBlock(x, y, z).getSelectedBlockBody
            }
        }
        aabbs
    }

    def setTileEntity(blockPos: BlockPos, tileEntityIn: TileEntity): Unit = {

        val chunk = getChunk(blockPos)
        if (chunk != null) chunk.addTileEntity(blockPos, tileEntityIn)
        addTileEntity(tileEntityIn)

    }

    def isAirBlock(blockPos: BlockPos): Boolean = if (blockPos.isValid(this)) getChunk(blockPos).isAirBlock(blockPos) else true

    def isAirBlock(x:Int,y:Int,z:Int): Boolean = getChunkBlockPos(x,y,z).isAirBlock(x,y,z)

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
        if(!this.isInstanceOf[WorldServer]){
            worldRenderer.reRender(blockState.pos)
        }

        val chunk = getChunk(blockState.pos)
        markAndNotifyBlock(blockState, chunk)
        chunk.setBlock(blockState)
    }
    def markAndNotifyBlock(pos: BlockState, chunk: Chunk) {

        if (chunk == null || chunk.isPopulated) notifyBlockUpdate(pos)
        /*
                        if (!isRemote && (flags & 1) != 0) {
                            notifyNeighborsRespectDebug(pos, iblockstate.getBlock)
                          //  if (newState.hasComparatorInputOverride) this.updateComparatorOutputLevel(pos, newState.getBlock)
                        }
        */
    }

    def notifyBlockUpdate(pos: BlockState) {

        for (i <- eventListeners.indices) {
            eventListeners(i).notifyBlockUpdate(this, pos)
        }

    }
    def addEventListener(listener: IWorldEventListener) {
        eventListeners += listener
    }
    def getTileEntity(blockPos: BlockPos): TileEntity ={
        if (!blockPos.isValid(this)){
            null
        } else {
            getChunk(blockPos).getTileEntity(blockPos)
        }
    }

    def spawnEntityInWorld(entity: Entity): Unit = {
        val chunk = getChunkBlockPos( entity.posX toInt,entity.posY toInt,entity.posZ toInt)
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
        val raytraceresult2: RayTraceResult = new RayTraceResult()
        var chunk:Chunk = null

        var xBS,yBS,zBS = 0
        var k1:Int = 200
        while (k1 >=0) {
            k1-=1
            if (l == i && i1 == j && j1 == k) {
                return if (returnLastUncollidableBlock) raytraceresult2 else new RayTraceResult()
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
            new RayTraceResult()
    }


    def save(): Unit = {
        log.info("World saved...")
        log.info("Saving chunks for level \'{}\'/{}")
        saveAllChunks(true)
        log.info("Saving players for level \'{}\'/{}")
        //todo
//        entities.splitter. foreach {
//            case entityPlayer:EntityPlayer =>
//                saveHandler.writePlayerData(entityPlayer)
//            case _ =>
//        }
        log.info("World saved completed")
    }
    def saveAllChunks(p_73044_1: Boolean /*, progressCallback: IProgressUpdate*/) {

        chunkProvider.saveChunks(p_73044_1)
    }

}
