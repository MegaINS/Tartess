package ru.megains.tartess.common.tileentity

import ru.megains.tartess.client.renderer.gui.{GuiChest, GuiContainer}
import ru.megains.tartess.common.block.data.BlockPos
import ru.megains.tartess.common.container.{Container, ContainerChest}
import ru.megains.tartess.common.entity.player.EntityPlayer
import ru.megains.tartess.common.utils.Logger


class TileEntityChest(pos:BlockPos) extends TileEntityInventory(pos,40) with Logger[TileEntityChest]{

    log.info("Set tile entity")
    println(pos.x)
    println(pos.y)
    println(pos.z)
    override def getGui(player: EntityPlayer): GuiContainer = new GuiChest(player.inventory,this)

    override def getContainer(player: EntityPlayer): Container = new ContainerChest(player.inventory,this)
}
