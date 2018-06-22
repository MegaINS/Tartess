package ru.megains.tartess.block.data

import scala.collection.mutable

class BlockCell {

    def getBlock(x: Int, y: Int, z: Int):BlockState = blocks.find(bs => bs.pos.x == x && bs.pos.y == y && bs.pos.z == z).orNull

    def setBlock(x: Int, y: Int, z: Int, blockState: BlockState):Unit = {
        blocks.+=(blockState)
    }

    val blocks = new mutable.HashSet[BlockState]()

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
