package ru.megains.tartess.client.renderer.gui

import java.awt.Color

import ru.megains.tartess.client.Tartess
import ru.megains.tartess.client.renderer.gui.element.GuiElement
import ru.megains.tartess.client.renderer.mesh.Mesh

class GuiSlotWorld(id: Int, val worldName: String, tartess: Tartess) extends GuiElement(tartess) {

    val weight: Int = 400
    val height: Int = 60
    val positionX: Int =  (tar.window.width -weight)/2
    val positionY: Int = tartess.window.height -100- 70 * id

    val textMesh: Mesh = createString(worldName, Color.BLACK)
    val slotSelect: Mesh = createRect(weight, height, Color.LIGHT_GRAY)
    val slot: Mesh = createRect(weight, height, Color.WHITE)
    var select: Boolean = false


    def draw(mouseX: Int, mouseY: Int): Unit = {

        val background: Mesh = if (select) slotSelect else slot

        drawObject(positionX, positionY, 1, background)

        drawObject(positionX + 10, positionY +22, 1, textMesh)

    }

    def isMouseOver(mouseX: Int, mouseY: Int): Boolean = {
        mouseX >= positionX && mouseX <= positionX + weight && mouseY >= positionY && mouseY <= positionY + height
    }
}
