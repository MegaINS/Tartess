package ru.megains.tartess.entity.item

import ru.megains.nbt.tag.NBTCompound
import ru.megains.tartess.entity.Entity
import ru.megains.tartess.item.Item
import ru.megains.tartess.item.itemstack.ItemPack

class EntityItem() extends Entity(0.25f*16, 0.25f*16, 0.25f*16) {

   // setSize(0.25f, 0.25f, 0.25f)


    var itemStack: ItemPack = _

    def setItem(itemStackIn: ItemPack): Unit ={
        itemStack = itemStackIn
    }
    override def update(): Unit = {
        motionY -= 0.01f*16

        moveFlying(0, 0, if (onGround) 0.03f else 0.01f)
        move(motionX, motionY, motionZ)
    }

    override def writeEntityToNBT(compound: NBTCompound): Unit = {
        val inventory = compound.createCompound("itemStack")
        if (itemStack != null) {
            inventory.setValue("id", Item.getIdFromItem(itemStack.item))
            inventory.setValue("stackSize", itemStack.stackSize)
        } else {
            inventory.setValue("id", -1)
        }

    }

    override def readEntityFromNBT(compound: NBTCompound): Unit = {
        val inventory = compound.getCompound("itemStack")
        val id: Int = inventory.getInt("id")
        if (id != -1) {
            itemStack = new ItemPack(Item.getItemById(id), inventory.getInt("stackSize"))
        }
    }
}
