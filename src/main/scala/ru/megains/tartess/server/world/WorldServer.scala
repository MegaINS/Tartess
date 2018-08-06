package ru.megains.tartess.server.world

import ru.megains.tartess.common.block.data.BlockPos
import ru.megains.tartess.common.world.World
import ru.megains.tartess.common.world.data.AnvilSaveHandler
import ru.megains.tartess.server.{EntityTracker, PlayerChunkMap, TartessServer}

class WorldServer(val server:TartessServer,val saveHandler:AnvilSaveHandler) extends World(saveHandler){

    val spawnPoint: BlockPos = new BlockPos(0, 20*16, 0)
    val playerManager: PlayerChunkMap = new PlayerChunkMap(this)
    val entityTracker: EntityTracker = new EntityTracker(this)
    var disableLevelSaving: Boolean = false

    override def update(): Unit = {
        super.update()

        playerManager.tick()
    }

}
