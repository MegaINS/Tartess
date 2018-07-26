package ru.megains.tartess.client.network.status.client

import java.io.IOException

import ru.megains.tartess.common.network.packet.{PacketBuffer, PacketWrite}

class CPacketServerQuery extends PacketWrite{
//    /**
//      * Reads the raw packet data from the data stream.
//      */
//    @throws[IOException]
//    def readPacketData(buf: PacketBuffer) {
//    }

    /**
      * Writes the raw packet data to the data stream.
      */
    @throws[IOException]
    def writePacketData(buf: PacketBuffer) {
    }

//    /**
//      * Passes this Packet on to the NetHandler for processing.
//      */
//    def processPacket(handler: NetHandlerStatusServer) {
//        handler.processServerQuery(this)
//    }
}
