package ru.megains.tartess.server.network.packet.status

import ru.megains.tartess.common.network.handler.INetHandler
import ru.megains.tartess.server.network.packet.status.client.{CPacketPing, CPacketServerQuery}

trait INetHandlerStatusServer extends INetHandler {
    def processPing(packetIn: CPacketPing)

    def processServerQuery(packetIn: CPacketServerQuery)
}
