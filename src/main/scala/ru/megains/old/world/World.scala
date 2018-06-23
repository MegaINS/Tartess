package ru.megains.old.world

import ru.megains.old.block.Block
import ru.megains.old.blockdata.BlockWorldPos
import ru.megains.old.physics.AxisAlignedBB
import ru.megains.old.register.Blocks
import ru.megains.old.util.RayTraceResult
import ru.megains.old.utils.MathHelper
import ru.megains.old.world.chunk.Chunk
import ru.megains.tartess.block.data.BlockDirection
import ru.megains.tartess.utils.Vec3f

import scala.collection.mutable
import scala.util.Random


class World(val length: Int,val height: Int,val width: Int) {

    val chunks: mutable.HashMap[Long, Chunk] = new mutable.HashMap[Long, Chunk]
    var worldRenderer:WorldRenderer = null
    val rand:Random = new Random()
   // val entitiesItem:ArrayBuffer[EntityItem] = new ArrayBuffer[EntityItem]()



    def init() {


        for(x <- -4 to 3;y <- -4 to 3;z <- -4 to 3){
          //  chunks += Chunk.getIndex(x, y, z) -> ChunkLoader.load(this,x,y,z)
        }

//        for(i<-1 to 10){
//            val entity = new EntityItem(GameRegister.getItemById(rand.nextInt(4)+2))
//            entity.setWorld(this)
//            entity.setPosition(rand.nextInt(i)-i/2,5,rand.nextInt(i)-i/2)
//            entitiesItem += entity
//        }

        
    }

    def update(){
       // entitiesItem.foreach(_.update())

        chunks.values.foreach(_.updateRandomBlocks(rand))


    }

    def  setBlock(pos:BlockWorldPos ,block:Block ) {
        if(!validBlockPos(pos)){return;}


        getChunk(pos).setBlockWorldCord(pos,block)
        worldRenderer.reRender(pos)
    }

    def  setAirBlock(pos:BlockWorldPos) {
        setBlock(pos,Blocks.air)
    }


//    def setEntity(entity:Entity): Unit ={
//        entitiesItem += entity.asInstanceOf
//    }

    def getChunk(blockPos: BlockWorldPos): Chunk = getChunk(blockPos.worldX >> 4, blockPos.worldY >> 4, blockPos.worldZ >> 4)

    def getChunk(x: Int, y: Int, z: Int): Chunk = {
        try{
            chunks(Chunk.getIndex(x, y, z))

        }catch {
            case e:NoSuchElementException =>
                println(x+" "+y+" "+z)
                null
        }

    }
    def isAirBlock(blockPos: BlockWorldPos): Boolean = if ( validBlockPos(blockPos)) getChunk(blockPos).isAirBlockWorldCord(blockPos) else true


    def getBlock(pos: BlockWorldPos): Block = if (!validBlockPos(pos)) Blocks.air else getChunk(pos).getBlockWorldCord(pos)

    def validBlockPos(pos: BlockWorldPos): Boolean = !(pos.worldZ < -width || pos.worldY < -height || pos.worldX < -length) && !(pos.worldZ > width - 1 || pos.worldY > height - 1 || pos.worldX > length - 1)

    def isOpaqueCube(blockPos:BlockWorldPos ):Boolean = getBlock(blockPos).isOpaqueCube

    def addBlocksInList(aabb: AxisAlignedBB): mutable.ArrayBuffer[AxisAlignedBB] ={
        var x0: Int = Math.floor(aabb.getMinX).toInt
        var y0: Int = Math.floor(aabb.getMinY).toInt
        var z0: Int = Math.floor(aabb.getMinZ).toInt
        var x1: Int = Math.ceil(aabb.getMaxX).toInt
        var y1: Int = Math.ceil(aabb.getMaxY).toInt
        var z1: Int = Math.ceil(aabb.getMaxZ).toInt
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
        var blockPos: BlockWorldPos = null
        val aabbs =  mutable.ArrayBuffer[AxisAlignedBB]()

        for(x<-x0 to x1;y<-y0 to y1;z<-z0 to z1) {

            blockPos = new BlockWorldPos(x, y, z)
            if (!isAirBlock(blockPos)) {
                aabbs += getBlock(blockPos).getBoundingBox(blockPos/*,MultiBlockPos.default*/)
            }
        }
        aabbs
    }

    def  save(): Unit = {
          chunks.values.foreach(_.save())
    }

    def rayTraceBlocks(vec1: Vec3f, vec32: Vec3f, stopOnLiquid: Boolean, ignoreBlockWithoutBoundingBox: Boolean, returnLastUncollidableBlock: Boolean): RayTraceResult = {

        var vec31: Vec3f = vec1
        val i: Int = MathHelper.floor_double(vec32.x)
        val j: Int = MathHelper.floor_double(vec32.y)
        val k: Int = MathHelper.floor_double(vec32.z)
        var l: Int = MathHelper.floor_double(vec31.x)
        var i1: Int = MathHelper.floor_double(vec31.y)
        var j1: Int = MathHelper.floor_double(vec31.z)
        var blockpos: BlockWorldPos = null
        val raytraceresult2: RayTraceResult = null


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
            blockpos = new BlockWorldPos(l, i1, j1)
            val block1: Block = getBlock(blockpos)
            if (block1 != null) {
                val raytraceresult1: RayTraceResult = block1.collisionRayTrace(this, blockpos, vec31, vec32)
                if (raytraceresult1 != null) {
                    return raytraceresult1
                }
            }

        }
        if (returnLastUncollidableBlock) raytraceresult2 else null

    }




}
