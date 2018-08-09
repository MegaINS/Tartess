package ru.megains.tartess.common.network.packet.play.client

import ru.megains.tartess.client.Tartess
import ru.megains.tartess.common.block.data.{BlockDirection, BlockState}
import ru.megains.tartess.common.network.handler.INetHandlerPlayServer
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}
import ru.megains.tartess.common.register.Blocks
import ru.megains.tartess.common.utils.{RayTraceResult, RayTraceType, Vec3f}

class CPacketPlayerMouse extends Packet[INetHandlerPlayServer]{

    var button:Int = -1
    var action:Int = -1
    var rayTraceResult:RayTraceResult = _
    var blockState:BlockState = _
    def this(buttonIn:Int,actionIn:Int,tartess: Tartess){
        this()
        button = buttonIn
        action = actionIn
        rayTraceResult = tartess.objectMouseOver
        blockState = tartess.blockSelectPosition
    }


    override def writePacketData(buf: PacketBuffer): Unit = {

        buf.writeInt(button)
        buf.writeInt(action)
        buf.writeByte(rayTraceResult.rayTraceType.id)
        if( rayTraceResult.rayTraceType!=RayTraceType.VOID){
            buf.writeBlockPos(rayTraceResult.blockPos)
            buf.writeInt(Blocks.getIdByBlock(rayTraceResult.block) )
        }

        buf.writeBlockState(blockState)
    }

    override def readPacketData(buf: PacketBuffer): Unit = {
        button = buf.readInt()
        action = buf.readInt()
        val rayTraceType = RayTraceType(buf.readByte())
        if( rayTraceType!=RayTraceType.VOID){
            rayTraceResult = new RayTraceResult(rayTraceType,new Vec3f(),BlockDirection.NONE,buf.readBlockPos(),Blocks.getBlockById( buf.readInt()))
        }else{
            rayTraceResult = new RayTraceResult()
        }

        blockState = buf.readBlockState()
    }

    override def processPacket(handler: INetHandlerPlayServer): Unit = {
        handler.processPlayerMouse(this)
    }
}
