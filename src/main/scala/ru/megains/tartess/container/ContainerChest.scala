package ru.megains.tartess.container

import ru.megains.tartess.inventory.{InventoryPlayer, Slot}
import ru.megains.tartess.tileentity.TileEntityChest

class ContainerChest(inventoryPlayer: InventoryPlayer,inventoryTest: TileEntityChest) extends Container{


    for (i <- 0 to 9;
         j <- 0 to 3) {
        addSlotToContainer(new Slot(inventoryTest, i + j * 10, 164 + i * 48, 236 + j * 46))
    }

    for (i <- 0 to 9) {
        addSlotToContainer(new Slot(inventoryPlayer, i, 164 + i * 48, 6))
    }
    for (i <- 0 to 9;
         j <- 0 to 2) {
        addSlotToContainer(new Slot(inventoryPlayer, 10 + i + j * 10, 164 + i * 48, 78 + j * 46))
    }


}
