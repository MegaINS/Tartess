package ru.megains.tartess.block.data

import ru.megains.tartess.world.World
import ru.megains.tartess.world.chunk.Chunk

class BlockPos(val x:Int,val y:Int,val z:Int) {

    def rendX: Float = (x&255) / 16f
    def rendY: Float = (y&255) / 16f
    def rendZ: Float = (z&255) / 16f

    def isValid(world: World): Boolean =
        !(
             z < (-1 -world.width)  * Chunk.CHUNK_SIZE ||
             y < (-1 -world.height) * Chunk.CHUNK_SIZE ||
             x < (-1 -world.length) * Chunk.CHUNK_SIZE
         )&&
        !(
             z > (1 + world.width)  * Chunk.CHUNK_SIZE - 1 ||
             y > (1 + world.height) * Chunk.CHUNK_SIZE - 1 ||
             x > (1 + world.length) * Chunk.CHUNK_SIZE - 1
         )
}
