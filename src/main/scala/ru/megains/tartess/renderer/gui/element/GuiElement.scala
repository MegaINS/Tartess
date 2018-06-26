package ru.megains.tartess.renderer.gui.element

import java.awt.Color

import ru.megains.tartess.Tartess
import ru.megains.tartess.item.itemstack.ItemStack
import ru.megains.tartess.renderer.Renderer
import ru.megains.tartess.renderer.font.{FontRender, Fonts}
import ru.megains.tartess.renderer.gui.Gui
import ru.megains.tartess.renderer.item.ItemRender
import ru.megains.tartess.renderer.mesh.Mesh

abstract class GuiElement extends Gui {

    def this(tar: Tartess) {
        this()
        setData(tar)
    }

    var itemRender: ItemRender = _
    var renderer: Renderer = _
    var fontRender: FontRender = _
    var tar: Tartess = _


    def setData(tarIn: Tartess): Unit = {
        tar = tarIn
        itemRender = tar.itemRender
        renderer = tar.renderer
        fontRender = tar.fontRender


        initGui(tar)
    }

    def initGui(tar: Tartess): Unit = {}

    def drawObject(mesh: Mesh, xPos: Int, yPos: Int): Unit = super.drawObject(xPos, yPos, 1, mesh, renderer)

    def drawObject(xPos: Int, yPos: Int, scale: Float, mesh: Mesh): Unit = super.drawObject(xPos, yPos, scale, mesh, renderer)

    def createString(text: String, color: Color): Mesh = fontRender.createStringGui(text, color, Fonts.timesNewRomanR)

    def drawItemStack(itemStack: ItemStack, xPos: Int, yPos: Int): Unit = itemRender.renderItemStackToGui(xPos, yPos, itemStack)
}
