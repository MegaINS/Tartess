package ru.megains.tartess.tileentity

import ru.megains.tartess.block.data.BlockState
import ru.megains.tartess.world.World

trait TileEntityContainer {

    def createNewTileEntity(worldIn: World,blockState: BlockState): TileEntity
}
