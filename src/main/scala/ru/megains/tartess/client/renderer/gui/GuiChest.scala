package ru.megains.tartess.client.renderer.gui

import ru.megains.tartess.client.renderer.mesh.Mesh
import ru.megains.tartess.common.container.ContainerChest
import ru.megains.tartess.common.inventory.InventoryPlayer
import ru.megains.tartess.common.tileentity.TileEntityChest

class GuiChest(inventoryPlayer: InventoryPlayer, inventoryTest: TileEntityChest) extends GuiContainer(new ContainerChest(inventoryPlayer,inventoryTest)){

    var chestInventory: Mesh = _



    override def initGui(): Unit = {
        chestInventory = createTextureRect(500, 440, "gui/chestInventory")
        posX = (tar.window.width - 500)/2
    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        posX = (tar.window.width - 500)/2
        drawObject(chestInventory, 0, 0)
        super.drawScreen(mouseX, mouseY)
    }

    override def cleanup(): Unit = {
        chestInventory.cleanUp()
    }
}
