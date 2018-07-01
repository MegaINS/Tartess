package ru.megains.tartess.world.chunk.data

import ru.megains.tartess.register.Blocks
import ru.megains.tartess.world.World
import ru.megains.tartess.world.chunk.{BlockStorage, Chunk}

class ChunkGenerator(world: World) {



    def provideChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        val chunk =   new Chunk( new ChunkPosition(chunkX, chunkY, chunkZ),world)
        val blockStorage: BlockStorage = chunk.blockStorage
        val blockData = blockStorage.blocksId

        if (chunkY < 0) {
            for (i <- 0 until 4096) {
                blockData(i) = Blocks.getIdByBlock(Blocks.dirt).toShort
            }
       }

        chunk
    }
}

