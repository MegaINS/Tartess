package ru.megains.tartess.server.network.packet.status.server

import java.io.IOException

import ru.megains.tartess.common.network.packet.{PacketBuffer, PacketWrite}

class SPacketPong() extends PacketWrite {

    private var clientTime: Long = 0L

    def this(clientTimeIn: Long) {
        this()
        this.clientTime = clientTimeIn
    }

//    /**
//      * Reads the raw packet data from the data stream.
//      */
//    @throws[IOException]
//    def readPacketData(buf: PacketBuffer) {
//        this.clientTime = buf.readLong
//    }
//
//    /**
//      * Writes the raw packet data to the data stream.
//      */
    @throws[IOException]
    def writePacketData(buf: PacketBuffer) {
        buf.writeLong(this.clientTime)
    }

//    /**
//      * Passes this Packet on to the NetHandler for processing.
//      */
//    def processPacket(handler: INetHandlerStatusClient) {
//        handler.handlePong(this)
//    }
}
