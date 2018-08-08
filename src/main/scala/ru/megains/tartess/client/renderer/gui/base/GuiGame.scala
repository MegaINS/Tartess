package ru.megains.tartess.client.renderer.gui.base

import org.lwjgl.glfw.GLFW.{GLFW_KEY_E, GLFW_KEY_ESCAPE}
import ru.megains.tartess.common.network.packet.play.client.CPacketCloseWindow

class GuiGame extends GuiScreen {

    override def keyTyped(typedChar: Char, keyCode: Int): Unit = {
        keyCode match {
            case GLFW_KEY_E | GLFW_KEY_ESCAPE =>
                tar.playerController.net.sendPacket(new CPacketCloseWindow)
                tar.guiManager.setGuiScreen(null)
            case _ =>
        }

    }
}
