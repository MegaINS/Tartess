package ru.megains.tartess.inventory

import ru.megains.nbt.NBTType._
import ru.megains.nbt.tag.NBTCompound
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.item.Item
import ru.megains.tartess.item.itemstack.ItemStack
import ru.megains.tartess.register.GameRegister

class InventoryPlayer(val entityPlayer: EntityPlayer) extends AInventory {



    val mainInventory: Array[ItemStack] = new Array[ItemStack](40)
    var stackSelect: Int = 0
    var itemStack: ItemStack = _
    GameRegister.getItems.foreach(item=>
        addItemStackToInventory(new ItemStack(item,10))
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

    def addItemStackToInventory(itemStack: ItemStack): Boolean = {

      //  itemStack.item.itemType match {
//            case ItemType.single =>
//                val index = getEmptyStack
//                if (index != -1) {
//                    mainInventory(index) = itemStack
//                    true
//                } else {
//                    false
//                }

           // case ItemType.base =>
               val itemOp =  mainInventory.filter(_ ne null).find(_.item ==itemStack.item)
                if(itemOp.nonEmpty){
                    itemOp.get.stackSize += itemStack.stackSize
                   // itemOp.get.stackMass += itemStack.stackMass
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
//            case ItemType.mass =>
//                val itemOp =  mainInventory.filter(_ ne null).find(_.item ==itemStack.item)
//                if(itemOp.nonEmpty){
//                    itemOp.get.stackMass += itemStack.stackMass
//                    true
//                }else{
//                    val index = getEmptyStack
//                    if (index != -1) {
//                        mainInventory(index) = itemStack
//                        true
//                    } else {
//                        false
//                    }
//                }

      //  }



    }


    def getEmptyStack: Int = mainInventory.indexOf(null)

    def getStackSelect: ItemStack = mainInventory(stackSelect)


    override def getStackInSlot(index: Int): ItemStack = mainInventory(index)

    override def setInventorySlotContents(index: Int, itemStack: ItemStack): Unit = {
        mainInventory(index) = itemStack
    }

    override def decrStackSize(index: Int, size: Int): ItemStack = {

        val stack = mainInventory(index)
        var newStack: ItemStack = null
        if (stack ne null) {
           // stack.item.itemType match {
               // case ItemType.base | ItemType.single  =>
                    if (stack.stackSize <= size) {
                        newStack = stack
                        mainInventory(index) = null
                    } else {
                        newStack = stack.splitStack(size)
                        if (stack.stackSize < 1) {
                            mainInventory(index) = null
                        }
                    }
//                case ItemType.mass =>
//                    if (stack.stackMass <= size) {
//                        newStack = stack
//                        mainInventory(index) = null
//                    } else {
//                        newStack = stack.splitStack(size)
//                        if (stack.stackMass < 1) {
//                            mainInventory(index) = null
//                        }
//                    }
           // }

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
                val itemStack = new ItemStack(Item.getItemById(id), compound.getInt("stackSize"))
                mainInventory(i) = itemStack
            }
        }
    }

}

object InventoryPlayer {
    def isHotBar(index: Int): Boolean = index > -1 && index < hotBarSize

    val hotBarSize = 10


}
