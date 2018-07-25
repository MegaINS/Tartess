package ru.megains.tartess.client.renderer.gui.base

import java.awt.Color

import org.lwjgl.glfw.GLFW._
import ru.megains.tartess.client.Tartess
import ru.megains.tartess.common.entity.player.EntityPlayer
import ru.megains.tartess.client.renderer.gui.Gui
import ru.megains.tartess.client.renderer.gui.element.{GuiButton, GuiElement}
import ru.megains.tartess.client.renderer.mesh.Mesh

import scala.collection.mutable.ArrayBuffer

abstract private[base] class  GuiScreen extends GuiElement {




    val buttonList: ArrayBuffer[GuiButton] = new ArrayBuffer[GuiButton]()


    override def setData(tar: Tartess): Unit = {
        buttonList.foreach(_.clear())
        buttonList.clear()
        super.setData(tar)
    }


    def mouseReleased(x: Int, y: Int, button: Int): Unit = {}

    def mouseClicked(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {
        if (button == 0) {
            buttonList.foreach(
                guiButton => {
                    if (guiButton.isMouseOver(x, y)) {
                        actionPerformed(guiButton)
                        return
                    }
                }
            )
        }

    }

    def mouseClickMove(x: Int, y: Int): Unit = {}

    def actionPerformed(button: GuiButton) {}

    def keyTyped(typedChar: Char, keyCode: Int): Unit = {

    }


    def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        buttonList.foreach(_.draw(mouseX, mouseY))
    }

    def drawDefaultBackground(): Unit = {
        drawObject(GuiScreen.background, 0, 0)
    }

    def cleanup(): Unit = {
        buttonList.foreach(_.clear())
        buttonList.clear()
    }

    def tick(): Unit = {}
}

object GuiScreen extends Gui {

    val background: Mesh = createGradientRect(2000, 2000, new Color(128, 128, 128, 128), new Color(0, 0, 0, 128))
}

