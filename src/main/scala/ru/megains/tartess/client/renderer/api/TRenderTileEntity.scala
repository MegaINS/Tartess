package ru.megains.tartess.client.renderer.api

import ru.megains.tartess.common.tileentity.TileEntity
import ru.megains.tartess.common.world.World

trait TRenderTileEntity {

    def init():Unit

    def render(tileEntity: TileEntity, world: World): Boolean
}
