package ru.megains.tartess.client.world

import ru.megains.tartess.common.world.chunk.data.ChunkPosition
import ru.megains.tartess.common.world.chunk.{Chunk, ChunkVoid}
import ru.megains.tartess.common.world.{IChunkProvider, World}

import scala.collection.mutable

class ChunkProviderClient(world: World) extends IChunkProvider {

    val blankChunk = new ChunkVoid(world)
    val chunkMapping: mutable.HashMap[Long, Chunk] = new mutable.HashMap[Long, Chunk]

    override def loadChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        val key = Chunk.getIndex(chunkX, chunkY, chunkZ)
        if (chunkMapping.contains(key)) chunkMapping(key)
        else {
            val chunk: Chunk = new Chunk(new ChunkPosition(chunkX, chunkY, chunkZ),world)
            chunkMapping += key -> chunk
            chunk
        }
    }

    override def provideChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        chunkMapping.getOrElse(Chunk.getIndex(chunkX, chunkY, chunkZ), blankChunk)
    }

    def getLoadedChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = chunkMapping(Chunk.getIndex(chunkX, chunkY, chunkZ))

    override def unload(chunk: Chunk): Unit = {

    }

    override def saveChunks(p_186027_1: Boolean): Boolean = {
        true
    }
}
