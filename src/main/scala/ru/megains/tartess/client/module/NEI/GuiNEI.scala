package ru.megains.tartess.client.module.NEI

import ru.megains.tartess.client.renderer.gui.base.GuiInGui
import ru.megains.tartess.common.entity.player.EntityPlayer

class GuiNEI extends GuiInGui{
    
    val container  = new ContainerNEI
    
    override def initGui(): Unit = {
        posX = tar.window.width - 250
}

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        posX =  tar.window.width - 250
        container.slots.foreach(
            slot => {
                drawItemStack(slot.itemPack, slot.xPos, slot.yPos)
            }
        )

    }

    override def mouseClicked(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {
        container.mouseClicked(x-posX, y-posY, button, player)
    }
    def isMouseOverSlot(slot: SlotNEI, mouseX: Int, mouseY: Int): Boolean = {
        mouseX >= slot.xPos && mouseX <= slot.xPos + 40 && mouseY >= slot.yPos && mouseY <= slot.yPos + 40
    }
    override def cleanup(): Unit = {
    }
}
