package ru.megains.tartess.physics

import ru.megains.tartess.block.data.{BlockDirection, BlockSidePos, BlockSize}

class BoundingBox(var minX:Int = 0,
                  var minY:Int = 0,
                  var minZ:Int = 0,
                  var maxX:Int = 0,
                  var maxY:Int = 0,
                  var maxZ:Int = 0){

    def this(maxX:Int, maxY:Int, maxZ:Int){
        this(0,0,0,maxX,maxY,maxZ)
    }
    def this(blockSize: BlockSize){
        this(0,0,0,blockSize.x, blockSize.y, blockSize.z)
    }

    def sum(x: Int, y: Int, z: Int) = new BoundingBox(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z)

    def getSidePos(side: BlockDirection): BlockSidePos = {
        side match {
            case BlockDirection.EAST =>
                new BlockSidePos(maxX, minY, minZ, maxX, maxY - 1, maxZ - 1)
            case BlockDirection.WEST =>
                new BlockSidePos(minX, minY, minZ, minX, maxY - 1, maxZ - 1)
            case BlockDirection.SOUTH =>
                new BlockSidePos(minX, minY, maxZ, maxX - 1, maxY - 1, maxZ)
            case BlockDirection.NORTH =>
                new BlockSidePos(minX, minY, minZ, maxX - 1, maxY - 1, minZ)
            case BlockDirection.DOWN =>
                new BlockSidePos(minX, minY, minZ, maxX - 1, minY, maxZ - 1)
            case BlockDirection.UP =>
                new BlockSidePos(minX, maxY, minZ, maxX - 1, maxY, maxZ - 1)
            case _ =>
                null

        }

    }

    def pointIsCube(x: Int, y: Int, z: Int): Boolean =
                (x >= minX && x < maxX) &&
                (y >= minY && y < maxY) &&
                (z >= minZ && z < maxZ)
}
