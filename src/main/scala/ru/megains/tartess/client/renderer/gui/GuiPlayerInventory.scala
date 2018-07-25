package ru.megains.tartess.client.renderer.gui

import org.lwjgl.glfw.GLFW._
import ru.megains.tartess.common.entity.player.EntityPlayer
import ru.megains.tartess.client.renderer.mesh.Mesh

class GuiPlayerInventory(entityPlayer: EntityPlayer) extends GuiContainer(entityPlayer.inventoryContainer) {


    var playerInventory: Mesh = _


    override def initGui(): Unit = {
        playerInventory = createTextureRect(500, 240, "gui/playerInventory")
        posX = (tar.window.width -500)/2
    }



    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        posX = (tar.window.width - 500)/2
        drawObject(playerInventory, 0 , 0)
        super.drawScreen(mouseX, mouseY)
    }

    override def cleanup(): Unit = {
        playerInventory.cleanUp()
    }
}
