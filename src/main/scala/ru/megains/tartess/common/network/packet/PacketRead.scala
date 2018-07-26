package ru.megains.tartess.common.network.packet

import ru.megains.tartess.common.network.handler.INetHandler


abstract class PacketRead[T <: INetHandler] extends Packet[T]{

    def readPacketData(buf: PacketBuffer): Unit

    def processPacket(handler: T): Unit
}
