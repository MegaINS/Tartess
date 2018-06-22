package ru.megains.tech.world.storage

import ru.megains.old.register.Blocks
import ru.megains.tech.world.World
import ru.megains.tech.world.chunk.Chunk
import ru.megains.tech.world.chunk.data.{BlockStorage, ChunkPosition}

class ChunkGenerator(world: World) {



    def provideChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        val chunk =   new Chunk( new ChunkPosition(chunkX, chunkY, chunkZ),world)
        val blockStorage: BlockStorage = chunk.blockStorage
        val blockData = blockStorage.blocksId

        if (chunkY < 0) {
            for (i <- 0 until 4096) {
              //  blockData(i) = Blocks.getIdByBlock(Blocks.stone).toShort
            }
        } else {
            val array = world.heightMap.generateHeightMap(chunkX, chunkZ)
            for (x1 <- 0 to 15; y1 <- 0 to 15; z1 <- 0 to 15) {
                if (array(x1)(z1) > y1 + (chunkY * 16)) {
                    blockStorage.setWorldGet(x1, y1, z1, Blocks.stone)
                } else if (array(x1)(z1) == y1 + (chunkY * 16)) {
                    blockStorage.setWorldGet(x1, y1, z1, Blocks.grass)
                }
            }
        }

        chunk
    }
}
