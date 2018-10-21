package ru.megains.tartess.common.network.packet.play.server

import ru.megains.tartess.common.block.data.{BlockPos, BlockState}
import ru.megains.tartess.common.network.handler.INetHandlerPlayClient
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}
import ru.megains.tartess.common.register.Blocks
import ru.megains.tartess.common.world.World

class SPacketBlockChange extends Packet[INetHandlerPlayClient] {


    var block: BlockState = _

    def this(worldIn: World, posIn: BlockPos) {
        this()


        block = worldIn.getBlock(posIn)
        if(block.block == Blocks.air){
            block = new BlockState(Blocks.air,posIn)
        }
    }

    /**
      * Reads the raw packet data from the data stream.
      */

    def readPacketData(buf: PacketBuffer) {
        block =buf.readBlockState()
    }

    /**
      * Writes the raw packet data to the data stream.
      */

    def writePacketData(buf: PacketBuffer) {
        buf.writeBlockState(block)
    }

    /**
      * Passes this Packet on to the NetHandler for processing.
      */
    def processPacket(handler: INetHandlerPlayClient) {
        handler.handleBlockChange(this)
    }


}
