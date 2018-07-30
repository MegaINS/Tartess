package ru.megains.tartess.common.tileentity

import ru.megains.nbt.tag.NBTCompound
import ru.megains.tartess.common.block.data.BlockPos
import ru.megains.tartess.common.world.World

abstract class TileEntity(val pos:BlockPos) {

    def update(world: World): Unit = {

    }

    def writeToNBT(data: NBTCompound): Unit = {

    }

    def readFromNBT(data: NBTCompound): Unit= {

    }

}
