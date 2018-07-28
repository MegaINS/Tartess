package ru.megains.tartess.common.network.packet.play.server


import ru.megains.tartess.common.item.ItemStack
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.common.network.handler.INetHandlerPlayClient
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}

class SPacketWindowItems extends Packet[INetHandlerPlayClient] {


    var windowId: Int = 0
    var itemStacks: Array[ItemPack] = _


    def this(windowIdIn: Int, stacks: Array[ItemPack]) {
        this()
        windowId = windowIdIn
        itemStacks = stacks
    }


    def readPacketData(buf: PacketBuffer) {
        windowId = buf.readUnsignedByte
        itemStacks = new Array[ItemPack](buf.readShort)
        for (i <- itemStacks.indices) {
            itemStacks(i) = buf.readItemPackFromBuffer
        }
    }


    def writePacketData(buf: PacketBuffer) {
        buf.writeByte(windowId)
        buf.writeShort(itemStacks.length)
        for (itemStack <- itemStacks) {
            buf.writeItemPackToBuffer(itemStack)
        }
    }

    def processPacket(handler: INetHandlerPlayClient) {
        handler.handleWindowItems(this)
    }
}
