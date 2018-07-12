package ru.megains.tartess.container

import ru.megains.tartess.Tartess
import ru.megains.tartess.inventory.{InventoryPlayer, Slot}

class ContainerPlayerInventory(inventoryPlayer: InventoryPlayer) extends Container {

    for (i <- 0 to 9) {
        addSlotToContainer(new Slot(inventoryPlayer, i, (Tartess.tartess.window.width -500)/2 +14 + i * 48, 6))
    }
    for (i <- 0 to 9; j <- 0 to 2) {
        addSlotToContainer(new Slot(inventoryPlayer, 10 + i + j * 10, (Tartess.tartess.window.width -500)/2 +14 + i * 48, 78 + j * 46))
    }

}
