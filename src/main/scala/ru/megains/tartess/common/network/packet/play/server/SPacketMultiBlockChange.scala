package ru.megains.tartess.common.network.packet.play.server


import ru.megains.tartess.common.block.data.{BlockPos, BlockState}
import ru.megains.tartess.common.network.handler.INetHandlerPlayClient
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}
import ru.megains.tartess.common.world.chunk.Chunk

import scala.collection.mutable.ArrayBuffer


class SPacketMultiBlockChange() extends Packet[INetHandlerPlayClient] {


    var changedBlocks: Array[BlockState] = _

    def this(changedBlocksIn: ArrayBuffer[BlockPos], chunk: Chunk) {
        this()
        changedBlocks = new Array[BlockState](changedBlocksIn.length)
        for (i <- changedBlocksIn.indices) {
            changedBlocks(i) = chunk.getBlock(changedBlocksIn(i))
        }

    }


    def readPacketData(buf: PacketBuffer) {

        changedBlocks = new Array[BlockState](buf.readInt)
        for (i <- changedBlocks.indices) {
            changedBlocks(i) = buf.readBlockState()
        }
    }


    def writePacketData(buf: PacketBuffer) {

        buf.writeInt(changedBlocks.length)
        for (blockState <- changedBlocks) {
            buf.writeBlockState(blockState)
        }
    }


    def processPacket(handler: INetHandlerPlayClient) {
        handler.handleMultiBlockChange(this)
    }

}
