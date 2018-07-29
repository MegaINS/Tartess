package ru.megains.tartess.server.world

import ru.megains.tartess.common.block.data.BlockState
import ru.megains.tartess.common.world.{IWorldEventListener, World}
import ru.megains.tartess.server.TartessServer

class ServerWorldEventHandler(server: TartessServer, world: WorldServer) extends IWorldEventListener {
    override def notifyBlockUpdate(worldIn: World, pos: BlockState): Unit = {

        world.playerManager.markBlockForUpdate(pos.pos)

    }
}
