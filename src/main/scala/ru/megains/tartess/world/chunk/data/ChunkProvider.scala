package ru.megains.tartess.world.chunk.data

import ru.megains.tartess.world.World
import ru.megains.tartess.world.chunk.{Chunk, ChunkVoid}

import scala.collection.mutable

class ChunkProvider(world: World) {

    ChunkProvider.voidChunk = new ChunkVoid(world)
    val chunkMap = new mutable.HashMap[Long,Chunk]()
    val chunkGenerator: ChunkGenerator = new ChunkGenerator(world)
    def provideChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {

        var chunk: Chunk = loadChunk(chunkX, chunkY, chunkZ)

        if (chunk == null) {
            val i: Long = Chunk.getIndex(chunkX, chunkY, chunkZ)
            try {
                chunk = ChunkLoader.load(world,chunkX,chunkY,chunkZ)

            } catch {
                case throwable: Throwable =>
                    throwable.printStackTrace()
            }

            chunkMap += i -> chunk
        }

        chunk
    }

    def loadChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {

        var chunk: Chunk = getLoadedChunk(chunkX, chunkY, chunkZ)
        if (chunk == null) {

            chunk = chunkGenerator.provideChunk(chunkX, chunkY, chunkZ)

            if (chunk != null) {
                chunkMap += Chunk.getIndex(chunkX, chunkY, chunkZ) -> chunk
            }
        }
        chunk
    }

    def getLoadedChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        chunkMap.getOrElse(Chunk.getIndex(chunkX, chunkY, chunkZ), null)
    }
}
object ChunkProvider{
    var voidChunk:Chunk = _
}
