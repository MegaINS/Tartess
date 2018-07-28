package ru.megains.tartess.common.network.handler

import ru.megains.tartess.common.network.packet.login.client.CPacketLoginStart

trait INetHandlerLoginServer extends INetHandler {

    def processLoginStart(packetIn: CPacketLoginStart)

    //  def processEncryptionResponse(packetIn: CPacketEncryptionResponse)
}
