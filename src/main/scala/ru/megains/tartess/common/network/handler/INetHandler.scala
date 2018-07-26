package ru.megains.tartess.common.network.handler

import ru.megains.tartess.common.network.packet.Packet

abstract class INetHandler {


    def onDisconnect(msg: String)

    def disconnect(msg: String)

    def sendPacket(packetIn: Packet[_ <: INetHandler]) {}

}
