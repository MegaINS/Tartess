package ru.megains.tartess.inventory

import ru.megains.tartess.item.itemstack.ItemPack

class Slot(inventory: Inventory, val index: Int, val xPos: Int, val yPos: Int) {

    var slotNumber: Int = 0

    def isEmpty: Boolean = getStack == null

    def getStack: ItemPack = inventory.getStackInSlot(index)

    def putStack(itemStack: ItemPack): Unit = {
        inventory.setInventorySlotContents(index, itemStack)
    }

    def decrStackSize(size: Int): ItemPack = {
        inventory.decrStackSize(index, size)
    }
}
