package ru.megains.tartess.server.network.packet.status.client

import java.io.IOException

import ru.megains.tartess.common.network.packet.{PacketBuffer, PacketRead}
import ru.megains.tartess.server.network.NetHandlerStatusServer

class CPacketServerQuery extends PacketRead[NetHandlerStatusServer] {
    /**
      * Reads the raw packet data from the data stream.
      */
    @throws[IOException]
    def readPacketData(buf: PacketBuffer) {
    }

//    /**
//      * Writes the raw packet data to the data stream.
//      */
//    @throws[IOException]
//    def writePacketData(buf: PacketBuffer) {
//    }

    /**
      * Passes this Packet on to the NetHandler for processing.
      */
    def processPacket(handler: NetHandlerStatusServer) {
        handler.processServerQuery(this)
    }
}
