package ru.megains.tartess.world.chunk

class ChunkPosition(val x:Int,val y:Int,val z:Int) {


    val minXR: Int = x * 16
    val minYR: Int = y * 16
    val minZR: Int = z * 16

    def minXP: Int = x * Chunk.CHUNK_SIZE
    def minYP: Int = y * Chunk.CHUNK_SIZE
    def minZP: Int = z * Chunk.CHUNK_SIZE

    def maxXP: Int = minXP + 255
    def maxYP: Int = minYP + 255
    def maxZP: Int = minZP + 255

}
