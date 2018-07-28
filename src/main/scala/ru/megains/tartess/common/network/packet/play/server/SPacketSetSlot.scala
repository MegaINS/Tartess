package ru.megains.tartess.common.network.packet.play.server


import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.common.network.handler.INetHandlerPlayClient
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}


class SPacketSetSlot extends Packet[INetHandlerPlayClient] {

    var windowId: Int = 0
    var slot: Int = 0
    var item: ItemPack = _


    def this(windowIdIn: Int, slotIn: Int, itemIn: ItemPack) {
        this()
        windowId = windowIdIn
        slot = slotIn
        item = if (itemIn == null) null else itemIn
    }


    def processPacket(handler: INetHandlerPlayClient) {
        handler.handleSetSlot(this)
    }


    def readPacketData(buf: PacketBuffer) {
        windowId = buf.readByte
        slot = buf.readShort
        item = buf.readItemPackFromBuffer
    }


    def writePacketData(buf: PacketBuffer) {
        buf.writeByte(windowId)
        buf.writeShort(slot)
        buf.writeItemPackToBuffer(item)
    }
}
