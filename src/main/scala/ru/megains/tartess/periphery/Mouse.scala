package ru.megains.tartess.periphery

import org.lwjgl.glfw.GLFW._
import ru.megains.tartess.Tartess

object Mouse {

    var x: Double = 0
    var y: Double = 0
    var preX: Double = 0
    var preY: Double = 0
    var DWheel: Double = 0
    var windowId: Long = 0

    def getY: Int = Tartess.tartess.window.height - y toInt

    def getX: Int = x toInt

    def getDX: Double = x - preX

    def getDY: Double = (y - preY) * -1

    def getDWheel: Int = DWheel.toInt

    def update(window: Window): Unit = {

        preX = x
        preY = y
        DWheel = 0
        glfwPollEvents()
    }

    def init(window: Window, tartess:Tartess): Unit = {
        windowId = window.id
        glfwSetCursorPosCallback(window.id, (windowHnd, xpos, ypos) => {
            x = xpos
            y = ypos
        })
        glfwSetMouseButtonCallback(window.id, (windowHnd, button, action, mods) => {
            if (tartess.guiManager.isGuiScreen) tartess.guiManager.runTickMouse(button, action == GLFW_PRESS)
            else tartess.runTickMouse(button, action == GLFW_PRESS)
        })
        glfwSetScrollCallback(windowId, (window: Long, xoffset: Double, yoffset: Double) => {
            DWheel = yoffset
        })
    }

    def setGrabbed(mode: Boolean): Unit = {
        if (mode) {
            glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_DISABLED)
        } else {
            glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_NORMAL)
        }
    }
}
