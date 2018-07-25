package ru.megains.tartess.common.block.data

class BlockSidePos(val minX: Int,val  minY: Int,val  minZ: Int,val  maxX: Int,val  maxY: Int,val  maxZ: Int) {

    def sum(direction: BlockDirection) = BlockSidePos(
            minX + direction.x,
            minY + direction.y,
            minZ + direction.z,
            maxX + direction.x,
            maxY + direction.y,
            maxZ + direction.z
        )

    def sum(x: Int, y: Int, z: Int) = new BlockSidePos(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z)
}

object BlockSidePos{
    def apply(minX: Int, minY: Int, minZ: Int, maxX: Int, maxY: Int, maxZ: Int): BlockSidePos = new BlockSidePos(minX,minY,minZ,maxX,maxY,maxZ)
}