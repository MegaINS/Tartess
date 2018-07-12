package ru.megains.tartess.renderer.gui

import ru.megains.tartess.container.ContainerChest
import ru.megains.tartess.inventory.InventoryPlayer
import ru.megains.tartess.renderer.mesh.Mesh
import ru.megains.tartess.tileentity.TileEntityChest

class GuiChest(inventoryPlayer: InventoryPlayer, inventoryTest: TileEntityChest) extends GuiContainer(new ContainerChest(inventoryPlayer,inventoryTest)){

    var chestInventory: Mesh = _


    override def initGui(): Unit = {
        chestInventory = createTextureRect(500, 440, "gui/chestInventory")
    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        drawObject(chestInventory, (tar.window.width -500)/2, 0)
        super.drawScreen(mouseX, mouseY)
    }

    override def cleanup(): Unit = {
        chestInventory.cleanUp()
    }
}
