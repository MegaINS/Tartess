package ru.megains.tartess.world.chunk

import ru.megains.old.util.RayTraceResult
import ru.megains.old.utils.Vec3f
import ru.megains.tartess.block.data.{BlockCell, BlockPos, BlockState}
import ru.megains.tartess.register.Blocks
import ru.megains.tartess.world.World

import scala.collection.mutable
import scala.language.postfixOps

class BlockStorage(position: ChunkPosition) {

    val blocksId: Array[Short] = new Array[Short](4096)
    val containers = new mutable.HashMap[Short,BlockCell]()


    def get(x: Int, y: Int, z: Int): BlockState = {
        val x1 = x>>3
        val y1 = y>>3
        val z1 = z>>3

        val index = getIndex(x1,y1,z1)
        blocksId(index) match {
            case -1 => containers(index).getBlock(x, y, z)
            case 0 => Blocks.air.blockState
            case id => new BlockState(Blocks.getBlockById(id),new BlockPos(position.minX +(x1<<3),position.minY  +(y1<<3),position.minZ +(z1<<3))/*,BlockDirection.EAST*/)
        }
    }



    def collisionRayTrace(world: World, blockpos: BlockPos, vec31: Vec3f, vec32: Vec3f):RayTraceResult = {
        val x1 = (blockpos.x & 127) >> 3
        val y1 = (blockpos.y & 127) >> 3
        val z1 = (blockpos.z & 127) >> 3

        val index = getIndex(x1,y1,z1)
        blocksId(index) match {
            case -1 => containers(index).collisionRayTrace( world, vec31, vec32)
            case 0 => null
            case id => new BlockState(Blocks.getBlockById(id),new BlockPos(position.minX +(x1<<3),position.minY  +(y1<<3),position.minZ +(z1<<3))).collisionRayTrace( world, vec31, vec32)
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
                case _ => blocks+= new BlockState(Blocks.getBlockById(id),new BlockPos(position.minX +(x<<3),position.minY  +(y<<3),position.minZ +(z<<3)))
            }

        }
        blocks
    }

    def setBlock(x: Int, y: Int, z: Int, blockState: BlockState) = {
        val index = getIndex(x>>3,y>>3,z>>3)
        containers.getOrElseUpdate(index,defaultValue ={
            blocksId(index) = -1
            new BlockCell(/*position.aabb*/)
        }).setBlock(x&3, y&3, z&3,blockState)
//        if(containers(index).blocksVal==0){
//            containers -= index
//            blocksId(index) = 0
//        }
    }

    def getIndex(x: Int, y: Int, z: Int): Short = x << 8 | y << 4 | z toShort
}
