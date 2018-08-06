package ru.megains.tartess.common.network.packet.status.server

import ru.megains.tartess.common.network.handler.INetHandlerStatusClient
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}
import ru.megains.tartess.server.ServerStatusResponse


class SPacketServerInfo() extends Packet[INetHandlerStatusClient] {
    private var response: ServerStatusResponse = null

    def this(responseIn: ServerStatusResponse) {
        this()
        this.response = responseIn
    }


    def readPacketData(buf: PacketBuffer) {

    }


    def writePacketData(buf: PacketBuffer) {

    }


    def processPacket(handler: INetHandlerStatusClient) {
        handler.handleServerInfo(this)
    }


}

