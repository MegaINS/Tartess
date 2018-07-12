package ru.megains.tartess.container

import ru.megains.tartess.Tartess
import ru.megains.tartess.inventory.{InventoryPlayer, Slot}
import ru.megains.tartess.tileentity.TileEntityChest

class ContainerChest(inventoryPlayer: InventoryPlayer,inventoryTest: TileEntityChest) extends Container{

    def posX(x:Int):Int = (Tartess.tartess.window.width -500)/2 +14 + x * 48

    for (i <- 0 to 9;
         j <- 0 to 3) {
        addSlotToContainer(new Slot(inventoryTest, i + j * 10, posX(i), 236 + j * 46))
    }

    for (i <- 0 to 9) {
        addSlotToContainer(new Slot(inventoryPlayer, i, posX(i), 6))
    }
    for (i <- 0 to 9;
         j <- 0 to 2) {
        addSlotToContainer(new Slot(inventoryPlayer, 10 + i + j * 10, posX(i), 78 + j * 46))
    }

}
