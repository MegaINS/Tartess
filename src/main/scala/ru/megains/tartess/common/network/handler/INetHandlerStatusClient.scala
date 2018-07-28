package ru.megains.tartess.common.network.handler

import ru.megains.tartess.common.network.packet.status.server.{SPacketPong, SPacketServerInfo}

trait INetHandlerStatusClient extends INetHandler {


    def handleServerInfo(packetIn: SPacketServerInfo)

    def handlePong(packetIn: SPacketPong)
}