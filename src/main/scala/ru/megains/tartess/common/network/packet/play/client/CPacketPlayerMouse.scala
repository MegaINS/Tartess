package ru.megains.tartess.common.network.packet.play.client

import ru.megains.tartess.common.network.handler.INetHandlerPlayServer
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}

class CPacketPlayerMouse extends Packet[INetHandlerPlayServer]{

    var button:Int = -1
    var action:Int = -1

    def this(buttonIn:Int,actionIn:Int){
        this()
        button = buttonIn
        action = actionIn
    }


    override def writePacketData(buf: PacketBuffer): Unit = {

        buf.writeInt(button)
        buf.writeInt(action)

    }

    override def readPacketData(buf: PacketBuffer): Unit = {
        button = buf.readInt()
        action = buf.readInt()
    }

    override def processPacket(handler: INetHandlerPlayServer): Unit = {
        handler.processPlayerMouse(this)
    }
}
