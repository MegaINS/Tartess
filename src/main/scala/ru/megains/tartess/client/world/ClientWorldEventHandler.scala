package ru.megains.tartess.client.world

import ru.megains.tartess.client.Tartess
import ru.megains.tartess.common.block.data.BlockState
import ru.megains.tartess.common.world.{IWorldEventListener, World}


class ClientWorldEventHandler (tar: Tartess, world: WorldClient) extends IWorldEventListener {
    override def notifyBlockUpdate(worldIn: World, pos: BlockState): Unit = {
        tar.worldRenderer.reRender(pos.pos)
     }
}
