package ru.megains.tartess.common.network.packet

abstract class PacketWrite extends Packet {

    def writePacketData(buf: PacketBuffer): Unit

}
