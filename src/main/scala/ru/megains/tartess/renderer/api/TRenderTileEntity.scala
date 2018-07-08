package ru.megains.tartess.renderer.api

import ru.megains.tartess.tileentity.TileEntity
import ru.megains.tartess.world.World

trait TRenderTileEntity {

    def init():Unit

    def render(tileEntity: TileEntity, world: World): Boolean
}
