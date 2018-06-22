package ru.megains.tartess.renderer.gui

import ru.megains.tartess.Tartess
import ru.megains.tartess.renderer.mesh.Mesh

class GuiUI extends GuiInGame{

    var target: Mesh = _

    override def initGui(tar: Tartess): Unit = {
        target = createTextureRect(40, 40, "gui/target")

    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {

        drawObject(target, 380, 280)

    }

    override def cleanup(): Unit = {
        target.cleanUp()

    }
}
