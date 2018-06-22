package ru.megains.tartess.world.chunk

class ChunkPosition(val x:Int,val y:Int,val z:Int) {


    val minXR: Int = x * 16
    val minYR: Int = y * 16
    val minZR: Int = z * 16


    val minX: Int = x * 128
    val minY: Int = y * 128
    val minZ: Int = z * 128

    val maxX: Int = x * 128 + 127
    val maxY: Int = y * 128 + 127
    val maxZ: Int = z * 128 + 127
}
