package ru.megains.tartess.common.network.handler

import ru.megains.tartess.common.network.packet.handshake.client.CHandshake

trait INetHandlerHandshake extends INetHandler{

    def processHandshake(handshake: CHandshake): Unit

}
