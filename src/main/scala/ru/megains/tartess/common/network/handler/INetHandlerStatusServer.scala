package ru.megains.tartess.common.network.handler

import ru.megains.tartess.common.network.packet.status.client.{CPacketPing, CPacketServerQuery}

trait INetHandlerStatusServer extends INetHandler {
    def processPing(packetIn: CPacketPing)

    def processServerQuery(packetIn: CPacketServerQuery)
}
