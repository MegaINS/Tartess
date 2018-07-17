package ru.megains.tartess.renderer.gui

import ru.megains.tartess.renderer.gui.base.GuiInGame
import ru.megains.tartess.renderer.mesh.Mesh

class GuiUI extends GuiInGame{

    var target: Mesh = _

    override def initGui(): Unit = {
        target = createTextureRect(40, 40, "gui/target")

    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        val width = (tar.window.width - 40)/2
        val height = (tar.window.height - 40)/2
        drawObject(target, width, height)

    }

    override def cleanup(): Unit = {
        target.cleanUp()

    }
}
