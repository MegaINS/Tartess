package ru.megains.tartess.renderer.gui

import ru.megains.tartess.renderer.gui.element.GuiButton

class GuiInGameMenu extends GuiScreen {


    override def initGui(): Unit = {
        buttonList += new GuiButton(0, tar, "Main menu", (tar.window.width -300)/2,310, 300, 50)
        buttonList += new GuiButton(1, tar, "Option",  (tar.window.width -300)/2, 380, 300, 50)
        buttonList += new GuiButton(2, tar, "Return to game",  (tar.window.width -300)/2, 450, 300, 50)

    }

    override def actionPerformed(button: GuiButton): Unit = {
        button.id match {

            case 0 => tar.loadWorld(null)
                tar.guiManager.setGuiScreen(new GuiMainMenu())

            case 1 => tar.guiManager.setGuiScreen(null)
            case 2 => tar.guiManager.setGuiScreen(null)
            case _ =>
        }
    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY)
    }


}
