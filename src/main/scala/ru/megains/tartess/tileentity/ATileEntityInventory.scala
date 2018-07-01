package ru.megains.tartess.tileentity

import ru.megains.tartess.container.Container
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.renderer.gui.GuiContainer

trait ATileEntityInventory {

    def getContainer(player: EntityPlayer): Container

    def getGui(player: EntityPlayer): GuiContainer

}

