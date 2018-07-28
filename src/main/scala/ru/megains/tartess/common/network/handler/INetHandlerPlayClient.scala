package ru.megains.tartess.common.network.handler

import ru.megains.tartess.common.network.packet.play.server._


trait INetHandlerPlayClient extends INetHandler {


    def handleChangeGameState(packetIn: SPacketChangeGameState): Unit

    def handlePlayerListItem(packetIn: SPacketPlayerListItem): Unit

    def handleWindowItems(items: SPacketWindowItems): Unit

    def handleSetSlot(slot: SPacketSetSlot): Unit

    def handleJoinGame(packetIn: SPacketJoinGame): Unit

    def handleHeldItemChange(packetIn: SPacketHeldItemChange): Unit

    def handlePlayerPosLook(packetIn: SPacketPlayerPosLook): Unit

    def handleChunkData(packetIn: SPacketChunkData): Unit

    def handleBlockChange(packetIn: SPacketBlockChange): Unit

    def handleMultiBlockChange(packetIn: SPacketMultiBlockChange): Unit
}
