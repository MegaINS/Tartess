package ru.megains.tartess.common.tileentity

import ru.megains.tartess.common.block.data.BlockPos
import ru.megains.tartess.common.container.{Container, ContainerChest}
import ru.megains.tartess.common.entity.player.EntityPlayer
import ru.megains.tartess.client.renderer.gui.{GuiChest, GuiContainer}
import ru.megains.tartess.common.utils.Logger
import ru.megains.tartess.common.world.World


class TileEntityChest(pos:BlockPos, world: World) extends TileEntityInventory(pos, world,40) with Logger[TileEntityChest]{

    log.info("Set tile entity")


    override def getGui(player: EntityPlayer): GuiContainer = new GuiChest(player.inventory,this)

    override def getContainer(player: EntityPlayer): Container = new ContainerChest(player.inventory,this)
}
