package ru.megains.tartess.common.tileentity

import ru.megains.tartess.common.block.data.BlockState
import ru.megains.tartess.common.world.World

trait TileEntityContainer {

    def createNewTileEntity(worldIn: World,blockState: BlockState): TileEntity
}
