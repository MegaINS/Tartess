package ru.megains.tartess.common.network.packet.status.client

import ru.megains.tartess.common.network.handler.INetHandlerStatusServer
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}

class CPacketServerQuery extends Packet[INetHandlerStatusServer] {

    def readPacketData(buf: PacketBuffer) {
    }


    def writePacketData(buf: PacketBuffer) {
    }


    def processPacket(handler: INetHandlerStatusServer) {
        handler.processServerQuery(this)
    }
}
