package ru.megains.tartess.common.network.handler

import ru.megains.tartess.common.network.packet.Packet

trait INetHandler {


    def onDisconnect(msg: String)

    def disconnect(msg: String)

    def sendPacket(packetIn: Packet[_ <: INetHandler]) {}

}
