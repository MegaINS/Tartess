package ru.megains.tartess.client.renderer.gui

import java.awt.Color

import ru.megains.tartess.client.renderer.gui.base.GuiMenu
import ru.megains.tartess.client.renderer.gui.element.GuiButton

class GuiDisconnected(screen: GuiMenu, reasonLocalizationKey: String, chatComp: String) extends GuiMenu with GuiText {


    override def initGui(): Unit = {
        addText("text", createString(chatComp, Color.BLACK))
        buttonList += new GuiButton(0, tar, "Cancel", 300, 200, 200, 50)
    }

    override def actionPerformed(button: GuiButton): Unit = {
        button.id match {
            case 0 => tar.guiManager.setGuiScreen(screen)
            case _ =>
        }
    }


    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        super.drawScreen(mouseX, mouseY)
        drawObject(300, 300, 2, text("text"))
    }
}
