package ru.megains.tartess.common.network.packet

import ru.megains.tartess.common.network.handler.INetHandler

abstract class Packet[T <: INetHandler] {

    def isImportant = false

    def writePacketData(buf: PacketBuffer): Unit

    def readPacketData(buf: PacketBuffer): Unit

    def processPacket(handler: T): Unit
}



