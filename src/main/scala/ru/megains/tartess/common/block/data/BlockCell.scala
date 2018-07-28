package ru.megains.tartess.common.block.data


import ru.megains.tartess.common.register.Blocks
import ru.megains.tartess.common.utils.{RayTraceResult, Vec3f}
import ru.megains.tartess.common.world.World
import ru.megains.tartess.common.world.chunk.data.ChunkPosition

import scala.collection.mutable

class BlockCell(val position: ChunkPosition/*,val x:Int,val y:Int,val z:Int*/) {

    val blocks = new mutable.HashSet[BlockState]()
    def isEmpty: Boolean = blocks.isEmpty

    def collisionRayTrace(world: World, vec31: Vec3f, vec32: Vec3f):RayTraceResult = {
        var rayTraceResult: RayTraceResult = null
        blocks.foreach{
            block =>
                rayTraceResult = block.collisionRayTrace(world, vec31, vec32)
                if (rayTraceResult != null) return rayTraceResult
        }
        rayTraceResult
    }

    def getBlock(x: Int, y: Int, z: Int):BlockState = {
        blocks.find(bs => bs.getSelectedBoundingBox.pointIsCube(x, y, z))
              .getOrElse(Blocks.air.airState)
    }

    def setBlock(blockState: BlockState):Unit = {

        if(blockState.block == Blocks.air){
            blocks -= getBlock(blockState.pos.x,blockState.pos.y,blockState.pos.z)
        }else{
            blocks.+=(blockState)
        }

    }

    def addBlocks(set: mutable.HashSet[BlockState]): Unit ={
        blocks.filter(block => position.pointIsCube(block.pos.x,block.pos.y,block.pos.z) ).foreach(set += _)
    }
}
