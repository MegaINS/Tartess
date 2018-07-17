package ru.megains.tartess.renderer.gui

import ru.megains.tartess.renderer.gui.base.GuiInGame
import ru.megains.tartess.renderer.mesh.Mesh

class GuiHotBar extends GuiInGame {

    var hotBar: Mesh = _
    var stackSelect: Mesh = _

    override def initGui(): Unit = {
        stackSelect = createTextureRect(56, 54, "gui/stackSelect")
        hotBar = createTextureRect(484, 52, "gui/hotBar")
    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {

        val inventory = tar.player.inventory
      val width = (tar.window.width - 484)/2
        drawObject(hotBar, width, 0)
        drawObject(stackSelect, width-2 + inventory.stackSelect * 48, 0)
        for (i <- 0 to 9) {
            drawItemStack(inventory.getStackInSlot(i), width+6 + i * 48, 6)
        }


    }

    override def cleanup(): Unit = {
        hotBar.cleanUp()
        stackSelect.cleanUp()
    }
}
