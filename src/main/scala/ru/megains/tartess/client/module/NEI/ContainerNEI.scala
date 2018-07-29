package ru.megains.tartess.client.module.NEI

import ru.megains.tartess.client.Tartess
import ru.megains.tartess.common.entity.player.EntityPlayer
import ru.megains.tartess.common.item.ItemType
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.common.register.GameRegister

import scala.collection.mutable.ArrayBuffer

class ContainerNEI {

    val slots: ArrayBuffer[SlotNEI] = new ArrayBuffer[SlotNEI]()
    var i = 0
    var j = 0

    GameRegister.getItems.foreach(item => {
        slots += new SlotNEI(new ItemPack(item), 14 + i * 48, 800 - 46 - j * 46)
        i += 1
        if (i == 5) {
            i = 0
            j += 1
        }

    })

    def getSlotAtPosition(x: Int, y: Int): SlotNEI = slots.find(isMouseOverSlot(_, x, y)).orNull

    def mouseClicked(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {
        val slot = getSlotAtPosition(x, y)
        if (slot != null) {
            slotClick(slot, button, player)
        }
    }

    def isMouseOverSlot(slot: SlotNEI, mouseX: Int, mouseY: Int): Boolean = {
        mouseX >= slot.xPos && mouseX <= slot.xPos + 40 && mouseY >= slot.yPos && mouseY <= slot.yPos + 40
    }

    def slotClick(slot: SlotNEI, button: Int, player: EntityPlayer): Unit = {

       val itemPack:ItemPack =  slot.itemPack.item.itemType match {
            case ItemType.SINGLE => new ItemPack(slot.itemPack.item)
            case ItemType.MASS => new ItemPack(slot.itemPack.item,if(button == 0) 1 else 1000)
            case ItemType.STACK => new ItemPack(slot.itemPack.item,if(button == 0) 1 else 64)
        }

        Tartess.tartess.playerController.net.sendPacket(new CPacketNEI(itemPack))
        player.inventory.addItemStackToInventory(itemPack)

    }
}
