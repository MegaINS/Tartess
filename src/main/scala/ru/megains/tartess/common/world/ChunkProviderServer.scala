package ru.megains.tartess.common.world

import java.io.IOException

import ru.megains.tartess.common.utils.Logger
import ru.megains.tartess.common.world.chunk.Chunk
import ru.megains.tartess.common.world.chunk.data.ChunkLoader
import ru.megains.tartess.server.world.WorldServer

import scala.collection.mutable

class ChunkProviderServer(world: WorldServer, chunkLoader: ChunkLoader, chunkGenerator: IChunkGenerator) extends IChunkProvider with Logger[ChunkProviderServer] {

    val chunkMap: mutable.HashMap[Long, Chunk] = new scala.collection.mutable.HashMap[Long, Chunk]()


    def canSave: Boolean = !world.disableLevelSaving

    def getLoadedChunks: Iterable[Chunk] = chunkMap.values

    def getLoadedChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        chunkMap.getOrElse(Chunk.getIndex(chunkX, chunkY, chunkZ), null)
    }

    override def loadChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        var chunk: Chunk = getLoadedChunk(chunkX, chunkY, chunkZ)
        if (chunk == null) {
            chunk = chunkLoader.loadChunk(world, chunkX, chunkY, chunkZ)
            if (chunk != null) {
                chunkMap += Chunk.getIndex(chunkX, chunkY, chunkZ) -> chunk
            }
        }
        chunk
    }

    override def provideChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
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
            //  chunk.onChunkLoad()
            //  chunk.populateChunk(this, chunkGenerator)
        }
        chunk
    }

    def saveChunks(p_186027_1: Boolean): Boolean = {

        chunkMap.values.foreach(
            (chunk) => {
                if (p_186027_1) saveChunkExtraData(chunk)
                if (chunk.needsSaving(p_186027_1)) {
                    saveChunkData(chunk)
                    // chunk.setModified(false)

                }
            }
        )
        chunkLoader.regionLoader.close()
        true
    }

    private def saveChunkExtraData(chunkIn: Chunk) {
        try
            chunkLoader.saveExtraChunkData(world, chunkIn)

        catch {
            case exception: Exception =>
                log.error("Couldn\'t save entities", exception.asInstanceOf[Throwable])
        }
    }

    private def saveChunkData(chunkIn: Chunk) {
        try
            //  chunkIn.setLastSaveTime(this.worldObj.getTotalWorldTime)
            chunkLoader.saveChunk(chunkIn)

        catch {
            case ioexception: IOException => {
                log.error("Couldn\'t save chunk", ioexception.asInstanceOf[Throwable])
            }

        }
    }


    override def unload(chunk: Chunk): Unit = {

    }
}
