package ru.megains.tartess.inventory

import ru.megains.nbt.NBTType._
import ru.megains.nbt.tag.NBTCompound
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.item.{Item, ItemType}
import ru.megains.tartess.item.itemstack.ItemPack
import ru.megains.tartess.register.GameRegister

class InventoryPlayer(val entityPlayer: EntityPlayer) extends Inventory {

    val mainInventory: Array[ItemPack] = new Array[ItemPack](40)
    var stackSelect: Int = 0
    var itemStack: ItemPack = _
    GameRegister.getItems.foreach(item=>{
        addItemStackToInventory(new ItemPack(item,10))
        addItemStackToInventory(new ItemPack(item,10))
    }

    )

    def changeStackSelect(value: Int): Unit = {
        var offset: Int = 0
        if (value > 0) {
            offset = 1
        }
        if (value < 0) {
            offset = -1
        }
        stackSelect += offset

        if (stackSelect > 9) {
            stackSelect = 0
        }
        if (stackSelect < 0) {
            stackSelect = 9
        }
    }

    def addItemStackToInventory(itemStack: ItemPack): Boolean = {

        itemStack.item.itemType match {
            case ItemType.SINGLE =>
                val index = getEmptyStack
                if (index != -1) {
                    mainInventory(index) = itemStack
                    true
                } else {
                    false
                }

            case ItemType.STACK =>
                val itemOp =  mainInventory.filter(_ ne null).find(_.item ==itemStack.item)
                if(itemOp.nonEmpty){
                    itemOp.get.stackSize += itemStack.stackSize
                    itemOp.get.stackMass += itemStack.stackMass
                    true
                }else{
                    val index = getEmptyStack
                    if (index != -1) {
                        mainInventory(index) = itemStack
                        true
                    } else {
                        false
                    }
                }
            case ItemType.MASS =>
                val itemOp =  mainInventory.filter(_ ne null).find(_.item ==itemStack.item)
                if(itemOp.nonEmpty){
                    itemOp.get.stackMass += itemStack.stackMass
                    true
                }else{
                    val index = getEmptyStack
                    if (index != -1) {
                        mainInventory(index) = itemStack
                        true
                    } else {
                        false
                    }
                }

        }
    }

    def getEmptyStack: Int = mainInventory.indexOf(null)

    def getStackSelect: ItemPack = mainInventory(stackSelect)

    override def getStackInSlot(index: Int): ItemPack = mainInventory(index)

    override def setInventorySlotContents(index: Int, itemStack: ItemPack): Unit = {
        mainInventory(index) = itemStack
    }

    override def decrStackSize(index: Int, size: Int): ItemPack = {

        val stack = mainInventory(index)
        var newStack: ItemPack = null
        if (stack != null) {
            stack.item.itemType match {
                case ItemType.STACK | ItemType.SINGLE  =>
                    if (stack.stackSize <= size) {
                        newStack = stack
                        mainInventory(index) = null
                    } else {
                        newStack = stack.splitStack(size)
                        if (stack.stackSize < 1) {
                            mainInventory(index) = null
                        }
                    }
                case ItemType.MASS =>
                    if (stack.stackMass <= size) {
                        newStack = stack
                        mainInventory(index) = null
                    } else {
                        newStack = stack.splitStack(size)
                        if (stack.stackMass < 1) {
                            mainInventory(index) = null
                        }
                    }
            }

        }
        newStack
    }

    def writeToNBT(data: NBTCompound): Unit = {
        val inventory = data.createList("mainInventory", EnumNBTCompound)
        for (i <- mainInventory.indices) {
            val compound = inventory.createCompound()
            val itemStack = mainInventory(i)

            if (itemStack != null) {
                compound.setValue("id", Item.getIdFromItem(itemStack.item))
                compound.setValue("stackSize", itemStack.stackSize)
            } else {
                compound.setValue("id", -1)
            }
        }
    }

    def readFromNBT(data: NBTCompound): Unit = {
        val inventory = data.getList("mainInventory")
        for (i <- mainInventory.indices) {
            val compound = inventory.getCompound(i)
            val id: Int = compound.getInt("id")
            if (id != -1) {
                val itemStack = new ItemPack(Item.getItemById(id), compound.getInt("stackSize"))
                mainInventory(i) = itemStack
            }
        }
    }

}

object InventoryPlayer {

    def isHotBar(index: Int): Boolean = index > -1 && index < hotBarSize

    val hotBarSize = 10

}
