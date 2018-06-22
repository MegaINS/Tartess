package ru.megains.tartess.world

import ru.megains.old.util.RayTraceResult
import ru.megains.old.utils.{MathHelper, Vec3f}
import ru.megains.tartess.block.data.{BlockDirection, BlockPos, BlockState}
import ru.megains.tartess.register.Blocks
import ru.megains.tartess.world.chunk.{Chunk, ChunkVoid}



class World {




    val length: Int = 1
    val width: Int = 1
    val height: Int = 1
    val chunkProvider:ChunkProvider = new ChunkProvider(this)


    def getChunk(x: Int, y: Int, z: Int): Chunk = {
        if((x <= length && x >= -length) && (z <= width  && z >= -width) && (y <= height && y >= -height)){
            chunkProvider.provideChunk(x,y,z)
        } else{
            ChunkVoid
        }

    }

    def getBlock(blockPos: BlockPos): BlockState = {
        if (!blockPos.isValid(this)) {
            return Blocks.air.blockState
        }
        val chunk = getChunk(blockPos.x>>7,blockPos.y>>7,blockPos.z>>7)
        chunk.getBlock(blockPos)
    }

    def setBlock(blockState: BlockState): Boolean ={
        if (!blockState.pos.isValid(this)) {
            return false
        }
        // worldRenderer.reRender(block.pos)
        val chunk = getChunk(blockState.pos.x&127,blockState.pos.y&127,blockState.pos.z&127)
        chunk.setBlock(blockState)


        //markAndNotifyBlock(pos, chunk, block, flag)
        true
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

            val l0 = (l>>3)<<3
            val i10 = (i1>>3)<<3
            val j110 = (j1>>3)<<3


            if(xBS != l0 || yBS != i10 || zBS != j110){
                xBS = l0
                yBS = i10
                zBS = j110
                blockpos = new BlockPos(xBS, yBS, zBS)
                chunk = getChunk(blockpos.x>>7,blockpos.y>>7,blockpos.z>>7)


                val raytraceresult1: RayTraceResult = chunk.collisionRayTrace(blockpos,  vec31, vec32)
                if (raytraceresult1 != null) {
                    return raytraceresult1
                }
                val block1: BlockState = getBlock(blockpos)
                if (block1 != null && block1 != Blocks.air.blockState) {
                    val raytraceresult1: RayTraceResult = block1.collisionRayTrace(this,  vec31, vec32)
                    if (raytraceresult1 != null) {
                        return raytraceresult1
                    }
                }
            }




        }
        null
       // if (returnLastUncollidableBlock) raytraceresult2 else null

    }

}
