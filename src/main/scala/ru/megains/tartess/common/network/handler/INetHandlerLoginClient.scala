package ru.megains.tartess.common.network.handler

import ru.megains.tartess.common.network.packet.login.server.{SPacketDisconnect, SPacketLoginSuccess}

trait INetHandlerLoginClient extends INetHandler {
    // def handleEncryptionRequest(packetIn: SPacketEncryptionRequest)

    def handleLoginSuccess(packetIn: SPacketLoginSuccess)

    def handleDisconnect(packetIn: SPacketDisconnect)

    // def handleEnableCompression(packetIn: SPacketEnableCompression)
}
