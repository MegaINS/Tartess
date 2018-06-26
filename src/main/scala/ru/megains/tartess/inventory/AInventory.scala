package ru.megains.tartess.inventory

import ru.megains.nbt.tag.NBTCompound
import ru.megains.tartess.item.itemstack.ItemStack

trait AInventory {


    def getStackInSlot(index: Int): ItemStack

    def setInventorySlotContents(index: Int, itemStack: ItemStack): Unit


    def decrStackSize(index: Int, size:Int): ItemStack

    def writeToNBT(data: NBTCompound): Unit

    def readFromNBT(data: NBTCompound): Unit



}
