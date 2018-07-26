package ru.megains.tartess.server.network.handshake.client

import ru.megains.tartess.common.network.packet.{PacketBuffer, PacketRead}
import ru.megains.tartess.server.network.{ConnectionState, NetHandlerHandshakeTCP}

class CHandshake() extends PacketRead[NetHandlerHandshakeTCP] {

   // var version: Int = 0
   // var ip: String = _
   // var port: Int = 0
    var connectionState: ConnectionState = _

//    def this(version: Int, ip: String, port: Int, connectionState: ConnectionState) {
//        this()
//        this.version = version
//        this.ip = ip
//        this.port = port
//        this.connectionState = connectionState
//    }

    override def readPacketData(packetBuffer: PacketBuffer): Unit = {
       // version = packetBuffer.readVarIntFromBuffer
       // ip = packetBuffer.readStringFromBuffer(255)
       // port = packetBuffer.readUnsignedShort
        connectionState = ConnectionState.getFromId(packetBuffer.readByte())
    }

//    override def writePacketData(packetBuffer: PacketBuffer): Unit = {
//        packetBuffer.writeVarIntToBuffer(version)
//        packetBuffer.writeStringToBuffer(ip)
//        packetBuffer.writeShort(port)
//        packetBuffer.writeVarIntToBuffer(connectionState.id)
//    }


    override def isImportant: Boolean = true


    override def processPacket(handler: NetHandlerHandshakeTCP) {
        handler.processHandshake(this)
    }
}
