package ru.megains.old.periphery

import org.lwjgl.glfw.GLFW._
import ru.megains.tartess.Tartess

object Keyboard {
    var windowId: Long = 0

    def init(window: Window, tartess:Tartess): Unit = {
        windowId = window.id
        glfwSetKeyCallback(windowId, (windowHnd: Long, key: Int, scancode: Int, action: Int, mods: Int) => {
           // if (orangeCraft.guiManager.isGuiScreen) orangeCraft.guiManager.runTickKeyboard(key, action, mods)
           // else
            tartess.runTickKeyboard(key, action, mods)

        })


    }
}