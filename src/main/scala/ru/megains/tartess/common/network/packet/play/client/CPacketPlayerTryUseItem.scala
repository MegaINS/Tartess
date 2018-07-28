package ru.megains.tartess.common.network.packet.play.client


import ru.megains.tartess.common.network.handler.INetHandlerPlayServer
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}

class CPacketPlayerTryUseItem() extends Packet[INetHandlerPlayServer] {


    def readPacketData(buf: PacketBuffer) {

    }


    def writePacketData(buf: PacketBuffer) {

    }

    def processPacket(handler: INetHandlerPlayServer) {
        handler.processPlayerBlockPlacement(this)
    }

}
