package ru.megains.tartess.client.renderer.gui

import ru.megains.tartess.client.renderer.gui.base.GuiMenu
import ru.megains.tartess.client.renderer.gui.element.GuiButton

class GuiPlayerSelect extends GuiMenu {


    override def initGui(): Unit = {
        buttonList += new GuiButton(0, tar, "Test_1",  (tar.window.width -300)/2, 450, 300, 50)
        buttonList += new GuiButton(1, tar, "Test_2",  (tar.window.width -300)/2, 380, 300, 50)
        buttonList += new GuiButton(2, tar, "Test_3",  (tar.window.width -300)/2, 310, 300, 50)
        buttonList += new GuiButton(3, tar, "Test_4",  (tar.window.width -300)/2, 240, 300, 50)

    }

    override def actionPerformed(button: GuiButton): Unit = {
        button.id match {
            case _ => tar.playerName = button.buttonText
                tar.guiManager.setGuiScreen(new GuiMainMenu())
        }
    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY)
    }
}
