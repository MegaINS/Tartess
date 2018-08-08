package ru.megains.tartess.common.network.packet.play.client

import ru.megains.tartess.common.network.handler.INetHandlerPlayServer
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}

class CPacketCloseWindow extends Packet[INetHandlerPlayServer]{

    override def writePacketData(buf: PacketBuffer): Unit = {

    }

    override def readPacketData(buf: PacketBuffer): Unit = {

    }

    override def processPacket(handler: INetHandlerPlayServer): Unit = {
        handler.processCloseWindow(this)
    }
}
