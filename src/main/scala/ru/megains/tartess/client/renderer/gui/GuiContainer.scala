package ru.megains.tartess.client.renderer.gui

import java.awt.Color

import org.lwjgl.glfw.GLFW.{GLFW_KEY_E, GLFW_KEY_ESCAPE}
import ru.megains.tartess.common.container.Container
import ru.megains.tartess.common.entity.player.EntityPlayer
import ru.megains.tartess.common.inventory.Slot
import ru.megains.tartess.client.renderer.gui.base.GuiGame
import ru.megains.tartess.client.renderer.mesh.Mesh


abstract class GuiContainer(val inventorySlots: Container) extends GuiGame {

    val rect: Mesh = createRect(40, 40, new Color(200, 255, 100, 100))

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {

        inventorySlots.inventorySlots.foreach(
            slot => {
                drawSlot(slot)
                if (inventorySlots.isMouseOverSlot(slot, mouseX-posX, mouseY-posY)) {
                    drawObject(rect, slot.xPos, slot.yPos)
                }
            }
        )

        drawItemStack(tar.player.inventory.itemStack, mouseX - 20-posX, mouseY - 15-posY)


    }


    def drawSlot(slot: Slot): Unit = {
        drawItemStack(slot.getStack, slot.xPos, slot.yPos)
    }

    override def cleanup(): Unit = {}

    override def mouseClicked(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {

        player.openContainer.mouseClicked(x-posX, y-posY, button, player)
      //  oc.playerController.windowClick(x, y, button, player: EntityPlayer)
    }

    def getSlotAtPosition(x: Int, y: Int): Slot = inventorySlots.inventorySlots.find(inventorySlots.isMouseOverSlot(_, x, y)).orNull
}
