package ru.megains.tartess.world.chunk.data

import java.io.IOException

import ru.megains.tartess.utils.Logger
import ru.megains.tartess.world.World
import ru.megains.tartess.world.chunk.{Chunk, ChunkVoid}

import scala.collection.mutable

class ChunkProvider(world: World,chunkLoader:ChunkLoader)  extends Logger[ChunkProvider]{

    ChunkProvider.voidChunk = new ChunkVoid(world)
    val chunkMap = new mutable.HashMap[Long,Chunk]()
    val chunkGenerator: ChunkGenerator = new ChunkGenerator(world)

    def getLoadedChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        chunkMap.getOrElse(Chunk.getIndex(chunkX, chunkY, chunkZ), null)
    }

    def provideChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        var chunk: Chunk = loadChunk(chunkX, chunkY, chunkZ)

        if (chunk == null) {
            val i: Long = Chunk.getIndex(chunkX, chunkY, chunkZ)
            try {
                chunk = chunkGenerator.provideChunk(chunkX, chunkY, chunkZ)
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
            chunk =  chunkLoader.loadChunk(world, chunkX, chunkY, chunkZ)
            if (chunk != null) {
                chunkMap += Chunk.getIndex(chunkX, chunkY, chunkZ) -> chunk
            }
        }
        chunk
    }





    def saveChunks(p_186027_1: Boolean): Boolean = {

        println()
        chunkMap.values.foreach(
            chunk => {

                saveChunkData(chunk)

            }
        )
        chunkLoader.regionLoader.close()
        true
    }
    def saveChunkData(chunkIn: Chunk) {
        try
            chunkLoader.saveChunk(chunkIn)

        catch {
            case ioexception: IOException => {
                log.error("Couldn\'t save chunk", ioexception.asInstanceOf[Throwable])
            }
        }
    }



}
object ChunkProvider{
    var voidChunk:Chunk = _
}
