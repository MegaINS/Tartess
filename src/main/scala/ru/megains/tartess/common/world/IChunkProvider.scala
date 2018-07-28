package ru.megains.tartess.common.world

import ru.megains.tartess.common.world.chunk.Chunk
import ru.megains.tartess.common.world.chunk.data.ChunkPosition

abstract class IChunkProvider {
    def unload(chunk: Chunk): Unit


    def loadChunk(pos: ChunkPosition): Chunk = {
        loadChunk(pos.x, pos.y, pos.z)
    }

    def loadChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk

    def provideChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk

    def saveChunks(p_186027_1: Boolean): Boolean

}
