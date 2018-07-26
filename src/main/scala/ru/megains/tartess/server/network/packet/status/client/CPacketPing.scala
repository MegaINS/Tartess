package ru.megains.tartess.server.network.packet.status.client

import java.io.IOException

import ru.megains.tartess.common.network.packet.{PacketBuffer, PacketRead}
import ru.megains.tartess.server.network.NetHandlerStatusServer

class CPacketPing() extends PacketRead[NetHandlerStatusServer] {
    private var clientTime: Long = 0L
    override def isImportant: Boolean = true
    def this(clientTimeIn: Long) {
        this()
        this.clientTime = clientTimeIn
    }

    /**
      * Reads the raw packet data from the data stream.
      */
    @throws[IOException]
    def readPacketData(buf: PacketBuffer) {
        clientTime = buf.readLong
    }

    /**
      * Writes the raw packet data to the data stream.
      */
    @throws[IOException]
    def writePacketData(buf: PacketBuffer) {
        buf.writeLong(clientTime)
    }

    /**
      * Passes this Packet on to the NetHandler for processing.
      */
    def processPacket(handler: NetHandlerStatusServer) {
        handler.processPing(this)
    }

    def getClientTime: Long = clientTime
}