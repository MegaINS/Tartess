package ru.megains.tartess.client.world

import ru.megains.tartess.common.world.ISaveHandler
import ru.megains.tartess.common.world.chunk.data.ChunkLoader

class SaveHandlerMP extends ISaveHandler {

    override def getChunkLoader: ChunkLoader = null
}
