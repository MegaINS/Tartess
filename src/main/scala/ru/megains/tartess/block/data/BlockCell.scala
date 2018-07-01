package ru.megains.tartess.block.data


import ru.megains.tartess.register.Blocks
import ru.megains.tartess.utils.{RayTraceResult, Vec3f}
import ru.megains.tartess.world.World

import scala.collection.mutable

class BlockCell {

    val blocks = new mutable.HashSet[BlockState]()
    var blocksVal = 0

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
        blocks.find(bs =>
                    bs
                    .getSelectedBoundingBox
                    .sum(bs.pos.x & 255, bs.pos.y & 255, bs.pos.z & 255)
                    .pointIsCube(x& 255, y& 255, z& 255))
              .getOrElse(Blocks.air.blockState)
    }

    def setBlock(blockState: BlockState):Unit = {

        if(blockState.block == Blocks.air){
            blocksVal-=1
            blocks -= getBlock(blockState.pos.x,blockState.pos.y,blockState.pos.z)
        }else{
            blocksVal+=1
            blocks.+=(blockState)
        }

    }



    def addBlocks(set: mutable.HashSet[BlockState]): Unit = {
        blocks.foreach(set += _)
       // blocks.filter(blockState =>

           // if(blockState ne null) {
              //  val pos = blockState.pos
              // chunk.checkCollision(new AABB(pos.x,pos.y,pos.z,pos.x+1,pos.y+1,pos.z+1))
           // } else false
      // ).foreach(set += _)
    }
}
