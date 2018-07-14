package ru.megains.tartess.inventory

import ru.megains.nbt.tag.NBTCompound
import ru.megains.tartess.item.itemstack.ItemPack

trait Inventory {

    def getStackInSlot(index: Int): ItemPack

    def setInventorySlotContents(index: Int, itemStack: ItemPack): Unit

    def decrStackSize(index: Int, size:Int): ItemPack

    def writeToNBT(data: NBTCompound): Unit

    def readFromNBT(data: NBTCompound): Unit

}
