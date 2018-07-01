package ru.megains.tartess.renderer.gui

import org.lwjgl.glfw.GLFW._
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.renderer.mesh.Mesh

class GuiPlayerInventory(entityPlayer: EntityPlayer) extends GuiContainer(entityPlayer.inventoryContainer) {


    var playerInventory: Mesh = _

    override def initGui(): Unit = {
        playerInventory = createTextureRect(500, 240, "gui/playerInventory")
    }



    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        drawObject(playerInventory, 150, 0)
        super.drawScreen(mouseX, mouseY)
    }

    override def cleanup(): Unit = {
        playerInventory.cleanUp()
    }
}
