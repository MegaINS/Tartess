package ru.megains.tartess.common.network.packet.status.server

import ru.megains.tartess.common.network.handler.INetHandlerStatusClient
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}

class SPacketPong() extends Packet[INetHandlerStatusClient] {

    private var clientTime: Long = 0L

    def this(clientTimeIn: Long) {
        this()
        this.clientTime = clientTimeIn
    }


    def readPacketData(buf: PacketBuffer) {
        this.clientTime = buf.readLong
    }


    def writePacketData(buf: PacketBuffer) {
        buf.writeLong(this.clientTime)
    }


    def processPacket(handler: INetHandlerStatusClient) {
        handler.handlePong(this)
    }
}
