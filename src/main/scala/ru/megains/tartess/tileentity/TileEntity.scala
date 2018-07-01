package ru.megains.tartess.tileentity

import ru.megains.nbt.tag.NBTCompound
import ru.megains.tartess.block.data.BlockPos
import ru.megains.tartess.world.World

abstract class TileEntity(val pos:BlockPos, val world: World) {

    def update(world: World): Unit = {

    }

    def writeToNBT(data: NBTCompound): Unit = {

    }

    def readFromNBT(data: NBTCompound): Unit= {

    }

}
