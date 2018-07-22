package ru.megains.tartess.physics

import ru.megains.tartess.block.data.BlockDirection

import scala.collection.mutable

class BoundingBoxes(val hashSet: mutable.HashSet[BoundingBox]){

    def rotate(side: BlockDirection): BoundingBoxes =  new BoundingBoxes(hashSet.map(_.rotate(side)))

    def sum(x: Int, y: Int, z: Int) =new BoundingBoxes(hashSet.map(_.sum(x, y, z)))

    def maxX: Int = {
        var maxX:Int = Int.MinValue
        hashSet.foreach{a =>maxX = maxX.max(a.maxX)}
        maxX
    }
    def maxY: Int = {
        var maxY:Int = Int.MinValue
        hashSet.foreach{ a =>maxY = maxY.max(a.maxY)}
        maxY
    }
    def maxZ: Int = {
        var maxZ:Int = Int.MinValue
        hashSet.foreach{ a =>maxZ = maxZ.max(a.maxZ)}
        maxZ
    }
    def minX: Int = {
        var minX:Int = Int.MaxValue
        hashSet.foreach{ a =>minX = minX.min(a.minX)}
        minX
    }
    def minY: Int = {
        var minY:Int = Int.MaxValue
        hashSet.foreach{ a =>minY = minY.min(a.minY)}
        minY
    }
    def minZ: Int = {
        var minZ:Int = Int.MaxValue
        hashSet.foreach{ a =>minZ = minZ.min(a.minZ)}
        minZ
    }


//    def getCopy = new BoundingBoxes(minX, minY, minZ, maxX, maxY, maxZ)
//    def sum(x: Int, y: Int, z: Int) = new BoundingBoxes(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z)
//
//    def getSidePos(side: BlockDirection): BlockSidePos = {
//        side match {
//            case BlockDirection.EAST =>
//                new BlockSidePos(maxX, minY, minZ, maxX, maxY - 1, maxZ - 1)
//            case BlockDirection.WEST =>
//                new BlockSidePos(minX, minY, minZ, minX, maxY - 1, maxZ - 1)
//            case BlockDirection.SOUTH =>
//                new BlockSidePos(minX, minY, maxZ, maxX - 1, maxY - 1, maxZ)
//            case BlockDirection.NORTH =>
//                new BlockSidePos(minX, minY, minZ, maxX - 1, maxY - 1, minZ)
//            case BlockDirection.DOWN =>
//                new BlockSidePos(minX, minY, minZ, maxX - 1, minY, maxZ - 1)
//            case BlockDirection.UP =>
//                new BlockSidePos(minX, maxY, minZ, maxX - 1, maxY, maxZ - 1)
//            case _ =>
//                null
//
//        }
//
//    }
//
    def pointIsCube(x: Int, y: Int, z: Int): Boolean = hashSet.exists(_.pointIsCube(x,y,z))

}
