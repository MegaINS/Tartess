package ru.megains.tartess.common.network.packet.login.server

import ru.megains.tartess.common.network.handler.INetHandlerLoginClient
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}

class SPacketLoginSuccess extends Packet[INetHandlerLoginClient] {


    override def isImportant: Boolean = true
    override def readPacketData(packetBuffer: PacketBuffer): Unit = {

    }

    override def writePacketData(packetBuffer: PacketBuffer): Unit = {

    }

    override def processPacket(handler: INetHandlerLoginClient): Unit = {
        handler.handleLoginSuccess(this)
    }
}
