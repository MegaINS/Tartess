package ru.megains.tartess.common.physics

import ru.megains.tartess.common.block.data.{BlockDirection, BlockSidePos}

class BoundingBox(var minX:Int = 0,
                  var minY:Int = 0,
                  var minZ:Int = 0,
                  var maxX:Int = 0,
                  var maxY:Int = 0,
                  var maxZ:Int = 0){



    def this(maxX:Int, maxY:Int, maxZ:Int){
        this(0,0,0,maxX,maxY,maxZ)
    }

    def this(size:Int){
        this(0,0,0,size,size,size)
    }

    def rotate(side: BlockDirection, boxes: BoundingBoxes): BoundingBox  = {
        side match {
            case BlockDirection.SOUTH=> new BoundingBox( boxes.maxZ - maxZ , minY,minX , boxes.maxZ - minZ, maxY, maxX  )
            case BlockDirection.WEST=> new BoundingBox(boxes.maxX - maxX, minY, boxes.maxZ - maxZ,boxes.maxX - minX, maxY,boxes.maxZ - minZ)
            case BlockDirection.NORTH=> new BoundingBox(minZ, minY,boxes.maxX - maxX, maxZ , maxY, boxes.maxX - minX  )
            case _  => getCopy
        }
    }

    def getCopy = new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ)

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
