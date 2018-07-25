package ru.megains.tartess.client.renderer.gui

import ru.megains.tartess.client.renderer.gui.base.GuiInGame
import ru.megains.tartess.client.renderer.mesh.Mesh

class GuiHotBar extends GuiInGame {

    var hotBar: Mesh = _
    var stackSelect: Mesh = _

    override def initGui(): Unit = {
        stackSelect = createTextureRect(56, 54, "gui/stackSelect")
        hotBar = createTextureRect(484, 52, "gui/hotBar")
        posX = (tar.window.width - 484)/2
    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {

        val inventory = tar.player.inventory
        posX = (tar.window.width - 484)/2
        drawObject(hotBar, 0, 0)
        drawObject(stackSelect, inventory.stackSelect * 48-2, 0)
        for (i <- 0 to 9) {
            drawItemStack(inventory.getStackInSlot(i), 6 + i * 48, 6)
        }


    }

    override def cleanup(): Unit = {
        hotBar.cleanUp()
        stackSelect.cleanUp()
    }
}
