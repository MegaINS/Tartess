package ru.megains.tartess.common.network.packet.play.server


import ru.megains.tartess.common.block.data.BlockPos
import ru.megains.tartess.common.network.handler.INetHandlerPlayClient
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}

class SPacketSpawnPosition extends Packet[INetHandlerPlayClient] {

    var blockPos: BlockPos = _

    def this(blockPosIn: BlockPos) {
        this()
        blockPos = blockPosIn
    }

    override def readPacketData(packetBuffer: PacketBuffer): Unit = {

    }

    override def writePacketData(packetBuffer: PacketBuffer): Unit = {

    }

    override def processPacket(handler: INetHandlerPlayClient): Unit = {

    }
}
