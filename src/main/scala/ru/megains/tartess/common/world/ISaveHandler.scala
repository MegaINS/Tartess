package ru.megains.tartess.common.world

import ru.megains.tartess.common.world.chunk.data.ChunkLoader

abstract class ISaveHandler {

    def getChunkLoader: ChunkLoader
}
