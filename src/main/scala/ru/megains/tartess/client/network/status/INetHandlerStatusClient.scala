package ru.megains.tartess.client.network.status

import ru.megains.tartess.client.network.status.server.{SPacketPong, SPacketServerInfo}
import ru.megains.tartess.common.network.handler.INetHandler


trait INetHandlerStatusClient extends INetHandler {


    def handleServerInfo(packetIn: SPacketServerInfo)

    def handlePong(packetIn: SPacketPong)
}