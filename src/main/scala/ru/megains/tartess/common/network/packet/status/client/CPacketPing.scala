package ru.megains.tartess.common.network.packet.status.client

import ru.megains.tartess.common.network.handler.INetHandlerStatusServer
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}

class CPacketPing() extends Packet[INetHandlerStatusServer] {

    var clientTime: Long = 0L

    override def isImportant: Boolean = true


    def this(clientTimeIn: Long) {
        this()
        this.clientTime = clientTimeIn
    }

    def readPacketData(buf: PacketBuffer) {
        clientTime = buf.readLong
    }


    def writePacketData(buf: PacketBuffer) {
        buf.writeLong(clientTime)
    }


    def processPacket(handler: INetHandlerStatusServer) {
        handler.processPing(this)
    }


}
