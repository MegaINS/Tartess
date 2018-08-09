package ru.megains.tartess.client.renderer.gui

import java.awt.Color

import ru.megains.tartess.client.renderer.gui.base.GuiGame
import ru.megains.tartess.client.renderer.gui.element.GuiButton

class GuiTestSet extends GuiGame with GuiText{



    override def initGui(): Unit = {
        addText("light.x", createString("Light.x", Color.WHITE))
        addText("light.v.x", createString(tar.renderer.dirLight.ambient.x.toString, Color.WHITE))
        buttonList += new GuiButton(0, tar, "+", 80,100, 30, 40)
        buttonList += new GuiButton(1, tar, "-",  160, 100, 30, 40)

        addText("light.y", createString("Light.y", Color.WHITE))
        addText("light.v.y", createString(tar.renderer.dirLight.ambient.y.toString, Color.WHITE))
        buttonList += new GuiButton(2, tar, "+", 80,60, 30, 40)
        buttonList += new GuiButton(3, tar, "-",  160, 60, 30, 40)

        addText("light.z", createString("Light.z", Color.WHITE))
        addText("light.v.z", createString(tar.renderer.dirLight.ambient.z.toString, Color.WHITE))
        buttonList += new GuiButton(4, tar, "+", 80,20, 30, 40)
        buttonList += new GuiButton(5, tar, "-",  160, 20, 30, 40)
    }

    override def actionPerformed(button: GuiButton): Unit = {
        button.id match {

            case 0 =>
                tar.renderer.dirLight.ambient.x += 0.01f
            case 1 =>
                tar.renderer.dirLight.ambient.x -= 0.01f

            case 2 =>
                tar.renderer.dirLight.ambient.y += 0.01f
            case 3 =>
                tar.renderer.dirLight.ambient.y -= 0.01f

            case 4 =>
                tar.renderer.dirLight.ambient.z += 0.01f
            case 5 =>
                tar.renderer.dirLight.ambient.z -= 0.01f
            case _ =>
        }
    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        super.drawScreen(mouseX, mouseY)
        drawObject(10, 110, 1, text("light.x"))
        drawObject(130, 110, 1, text("light.v.x"))

        drawObject(10, 70, 1, text("light.y"))
        drawObject(130, 70, 1, text("light.v.y"))

        drawObject(10, 30, 1, text("light.z"))
        drawObject(130, 30, 1, text("light.v.z"))
    }
    override def tick(): Unit = {
        addText("light.v.x", createString(tar.renderer.dirLight.ambient.x.toString, Color.WHITE))
        addText("light.v.y", createString(tar.renderer.dirLight.ambient.y.toString, Color.WHITE))
        addText("light.v.z", createString(tar.renderer.dirLight.ambient.z.toString, Color.WHITE))
    }
}
