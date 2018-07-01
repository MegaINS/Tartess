package ru.megains.tartess.world.chunk.data

import ru.megains.tartess.world.chunk.Chunk

class ChunkPosition(val x:Int,val y:Int,val z:Int) {

    def minXP: Int = x * Chunk.CHUNK_SIZE
    def minYP: Int = y * Chunk.CHUNK_SIZE
    def minZP: Int = z * Chunk.CHUNK_SIZE

    def maxXP: Int = minXP + 255
    def maxYP: Int = minYP + 255
    def maxZP: Int = minZP + 255

}
