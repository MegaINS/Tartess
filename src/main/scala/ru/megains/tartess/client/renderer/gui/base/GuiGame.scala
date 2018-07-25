package ru.megains.tartess.client.renderer.gui.base

import org.lwjgl.glfw.GLFW.{GLFW_KEY_E, GLFW_KEY_ESCAPE}

class GuiGame extends GuiScreen {

    override def keyTyped(typedChar: Char, keyCode: Int): Unit = {
        keyCode match {
            case GLFW_KEY_E | GLFW_KEY_ESCAPE => tar.guiManager.setGuiScreen(null)
            case _ =>
        }

    }
}
