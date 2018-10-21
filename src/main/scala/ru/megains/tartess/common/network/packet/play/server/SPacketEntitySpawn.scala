package ru.megains.tartess.common.network.packet.play.server

import ru.megains.tartess.common.entity.Entity
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}

class SPacketEntitySpawn extends Packet{



    def this(entity:Entity) {
        this()

    }

    override def writePacketData(buf: PacketBuffer): Unit = {

    }

    override def readPacketData(buf: PacketBuffer): Unit = {

    }

    override def processPacket(handler: Nothing): Unit = {

    }
}
