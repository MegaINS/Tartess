package ru.megains.tartess.tileentity

import ru.megains.nbt.NBTType.EnumNBTCompound
import ru.megains.nbt.tag.NBTCompound
import ru.megains.tartess.block.data.BlockPos
import ru.megains.tartess.inventory.Inventory
import ru.megains.tartess.item.Item
import ru.megains.tartess.item.itemstack.ItemStack
import ru.megains.tartess.world.World

abstract class TileEntityInventory(pos:BlockPos, world: World,slotSize:Int) extends TileEntity(pos, world) with Inventory with ATileEntityInventory{

    var slots:Array[ItemStack] = new Array[ItemStack](slotSize)

    override def getStackInSlot(index: Int): ItemStack = {
        slots(index)
    }

    override def setInventorySlotContents(index: Int, itemStack: ItemStack): Unit = {
        slots(index) = itemStack
    }

    override def decrStackSize(index: Int, size: Int): ItemStack = {

        val stack = slots(index)
        var newStack: ItemStack = null
        if (stack ne null) {

            if (stack.stackSize <= size) {
                newStack = stack
                slots(index) = null
            } else {
                newStack = stack.splitStack(size)
                if (stack.stackSize < 1) {
                    slots(index) = null
                }
            }
        }
        newStack
    }

    override def writeToNBT(data: NBTCompound): Unit = {
        super.writeToNBT(data)
        val inventory = data.createList("slots", EnumNBTCompound)
        for (i <- slots.indices) {
            val compound = inventory.createCompound()
            val itemStack = slots(i)

            if (itemStack != null) {
                compound.setValue("id", Item.getIdFromItem(itemStack.item))
                compound.setValue("stackSize", itemStack.stackSize)
            } else {
                compound.setValue("id", -1)
            }
        }
    }

    override def readFromNBT(data: NBTCompound): Unit = {
        super.readFromNBT(data)
        val inventory = data.getList("slots")
        for (i <- slots.indices) {
            val compound = inventory.getCompound(i)
            val id: Int = compound.getInt("id")
            if (id != -1) {
                val itemStack = new ItemStack(Item.getItemById(id), compound.getInt("stackSize"))
                slots(i) = itemStack
            }
        }
    }
}
