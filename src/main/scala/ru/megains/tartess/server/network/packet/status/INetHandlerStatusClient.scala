package ru.megains.tartess.server.network.packet.status

import ru.megains.tartess.common.network.handler.INetHandler
import ru.megains.tartess.server.network.packet.status.server.{SPacketPong, SPacketServerInfo}

trait INetHandlerStatusClient extends INetHandler {


    def handleServerInfo(packetIn: SPacketServerInfo)

    def handlePong(packetIn: SPacketPong)
}