package ru.megains.tartess.world.chunk


import ru.megains.tartess.block.data.{BlockCell, BlockPos, BlockState}
import ru.megains.tartess.register.Blocks
import ru.megains.tartess.utils.{RayTraceResult, Vec3f}
import ru.megains.tartess.world.World
import ru.megains.tartess.world.chunk.data.ChunkPosition

import scala.collection.mutable
import scala.language.postfixOps

class BlockStorage(position: ChunkPosition) {

    val blocksId: Array[Short] = new Array[Short](4096)
    val containers = new mutable.HashMap[Short,BlockCell]()

    def get(x: Int, y: Int, z: Int): BlockState = {
        val x1 = x>>4
        val y1 = y>>4
        val z1 = z>>4

        val index = getIndex(x1,y1,z1)
        blocksId(index) match {
            case -1 => containers(index).getBlock(x, y, z)
            case 0 => Blocks.air.blockState
            case id => new BlockState(Blocks.getBlockById(id),new BlockPos(position.minXP +(x1<<4),position.minYP  +(y1<<4),position.minZP +(z1<<4))/*,BlockDirection.EAST*/)
        }
    }

    def collisionRayTrace(world: World, blockpos: BlockPos, vec31: Vec3f, vec32: Vec3f):RayTraceResult = {
        val x1 = (blockpos.x & 255) >> 4
        val y1 = (blockpos.y & 255) >> 4
        val z1 = (blockpos.z & 255) >> 4

        val index = getIndex(x1,y1,z1)
        blocksId(index) match {
            case -1 => containers(index).collisionRayTrace( world, vec31, vec32)
            case 0 => null
            case id => new BlockState(Blocks.getBlockById(id),new BlockPos(position.minXP +(x1<<4),position.minYP  +(y1<<4),position.minZP +(z1<<4))).collisionRayTrace( world, vec31, vec32)
        }
    }

    def getBlocks: mutable.HashSet[BlockState] ={
        val blocks = new  mutable.HashSet[BlockState]()

        for (x <- 0 until 16;
             y <- 0 until 16;
             z <- 0 until 16) {

            val index = getIndex(x,y,z)
            val id =  blocksId(index)
            id match {
                case -1 => containers(index).addBlocks(blocks)
                case 0 =>
                case _ => blocks+= new BlockState(Blocks.getBlockById(id),new BlockPos(position.minXP +(x<<4),position.minYP  +(y<<4),position.minZP +(z<<4)))
            }

        }
        blocks
    }

    def setBlock(x: Int, y: Int, z: Int, blockState: BlockState): Unit = {
        val index = getIndex(x>>4,y>>4,z>>4)
        containers.getOrElseUpdate(index,defaultValue ={
            blocksId(index) = -1
            new BlockCell()
        }).setBlock(blockState)
        if(containers(index).blocksVal==0){
            containers -= index
            blocksId(index) = 0
        }
    }

    def getIndex(x: Int, y: Int, z: Int): Short = x << 8 | y << 4 | z toShort
}
