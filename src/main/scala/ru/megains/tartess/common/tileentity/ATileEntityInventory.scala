package ru.megains.tartess.common.tileentity

import ru.megains.tartess.common.container.Container
import ru.megains.tartess.common.entity.player.EntityPlayer
import ru.megains.tartess.client.renderer.gui.GuiContainer

trait ATileEntityInventory {

    def getContainer(player: EntityPlayer): Container

    //todo delete client
    def getGui(player: EntityPlayer): GuiContainer

}

