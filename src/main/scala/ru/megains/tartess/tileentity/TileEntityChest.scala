package ru.megains.tartess.tileentity

import ru.megains.tartess.block.data.BlockPos
import ru.megains.tartess.container.{Container, ContainerChest}
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.renderer.gui.{GuiChest, GuiContainer}
import ru.megains.tartess.utils.Logger
import ru.megains.tartess.world.World


class TileEntityChest(pos:BlockPos, world: World) extends TileEntityInventory(pos, world,40) with Logger[TileEntityChest]{

    log.info("Set tile entity")


    override def getGui(player: EntityPlayer): GuiContainer = new GuiChest(player.inventory,this)

    override def getContainer(player: EntityPlayer): Container = new ContainerChest(player.inventory,this)
}
