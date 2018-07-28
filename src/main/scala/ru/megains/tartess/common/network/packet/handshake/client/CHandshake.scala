package ru.megains.tartess.common.network.packet.handshake.client

import ru.megains.tartess.common.network.ConnectionState
import ru.megains.tartess.common.network.handler.INetHandlerHandshake
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}

class CHandshake() extends Packet[INetHandlerHandshake] {

    var version: Int = 0
   // var ip: String = _
   // var port: Int = 0
    var connectionState: ConnectionState = _

    def this(version: Int, ip: String, port: Int, connectionState: ConnectionState) {
        this()
        this.version = version
       // this.ip = ip
       // this.port = port
        this.connectionState = connectionState
    }

    override def readPacketData(packetBuffer: PacketBuffer): Unit = {
        version = packetBuffer.readInt
       // ip = packetBuffer.readStringFromBuffer(255)
       // port = packetBuffer.readUnsignedShort
        connectionState = ConnectionState.getFromId(packetBuffer.readByte())
    }

    override def writePacketData(packetBuffer: PacketBuffer): Unit = {
         packetBuffer.writeInt(version)
        // packetBuffer.writeStringToBuffer(ip)
        ///  packetBuffer.writeShort(port)
        packetBuffer.writeByte(connectionState.id)
    }


    override def isImportant: Boolean = true


    override def processPacket(handler: INetHandlerHandshake) {
        handler.processHandshake(this)
    }
}
