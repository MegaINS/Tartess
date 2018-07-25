package ru.megains.tartess.client.renderer.gui

import ru.megains.tartess.client.renderer.gui.base.GuiMenu
import ru.megains.tartess.client.renderer.gui.element.GuiButton

class GuiMainMenu extends GuiMenu {



    override def initGui(): Unit = {
        buttonList += new GuiButton(0, tar, "SingleGame",  (tar.window.width -300)/2, 450, 300, 50)
        buttonList += new GuiButton(1, tar, "MultiPlayerGame",  (tar.window.width -300)/2, 380, 300, 50)
        buttonList += new GuiButton(2, tar, "Option",  (tar.window.width -300)/2, 310, 300, 50)
        buttonList += new GuiButton(3, tar, "Exit game",  (tar.window.width -300)/2, 240, 300, 50)

    }

    override def actionPerformed(button: GuiButton): Unit = {
        button.id match {
            case 0 => tar.guiManager.setGuiScreen(new GuiSelectWorld(this))
            case 1 => tar.guiManager.setGuiScreen(new GuiMultiPlayer(this))
            case 3 => tar.running = false
            case _ =>
        }
    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY)
    }

}
