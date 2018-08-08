package ru.megains.tartess.common.network

import io.netty.util.AttributeKey
import ru.megains.tartess.client.module.NEI.CPacketNEI
import ru.megains.tartess.common.network.packet.Packet
import ru.megains.tartess.common.network.packet.handshake.client.CHandshake
import ru.megains.tartess.common.network.packet.login.client.CPacketLoginStart
import ru.megains.tartess.common.network.packet.login.server.{SPacketDisconnect, SPacketLoginSuccess}
import ru.megains.tartess.common.network.packet.play.client._
import ru.megains.tartess.common.network.packet.play.server._
import ru.megains.tartess.common.network.packet.status.client.{CPacketPing, CPacketServerQuery}
import ru.megains.tartess.common.network.packet.status.server.{SPacketPong, SPacketServerInfo}

import scala.collection.mutable

sealed abstract class ConnectionState(val name: String, val id: Int) {

    val packetsId = new mutable.HashMap[ Class[_ <: Packet[_]],Int]()
    val idPackets = new mutable.HashMap[ Int,Class[_ <: Packet[_]]]()

    def registerPacket(packet: Class[_<:Packet[_]]): Unit = {
        packetsId +=  packet -> packetsId.size
        idPackets +=  idPackets.size -> packet
    }

    def getPacketId(packet: Class[_<:Packet[_]]): Int = {
        packetsId(packet)
    }

    def getPacket(id: Int): Packet[_] = {
        val packet = idPackets(id)
        if (packet ne null) packet.newInstance() else null.asInstanceOf[Packet[_]]
    }
}


object ConnectionState {

    val PROTOCOL_ATTRIBUTE_KEY: AttributeKey[ConnectionState] = AttributeKey.newInstance[ConnectionState]("ConnectionState")

    val STATES_BY_CLASS = new mutable.HashMap[Class[_ <: Packet[_]], ConnectionState]()

    def getFromId(id: Int): ConnectionState = {
        STATES_BY_ID(id)
    }


    def getFromPacket(inPacket: Packet[_]): ConnectionState = {
        STATES_BY_CLASS(inPacket.getClass)
    }


    case object HANDSHAKING extends ConnectionState("HANDSHAKING", 0) {
               registerPacket(classOf[CHandshake])
    }


    case object STATUS extends ConnectionState("STATUS", 1) {

        registerPacket(classOf[CPacketServerQuery])
        registerPacket(classOf[SPacketServerInfo])
        registerPacket(classOf[CPacketPing])
        registerPacket(classOf[SPacketPong])

    }

    case object LOGIN extends ConnectionState("LOGIN", 2) {
        registerPacket(classOf[SPacketDisconnect])
        registerPacket(classOf[SPacketLoginSuccess])


        registerPacket(classOf[CPacketLoginStart])

    }


    case object PLAY extends ConnectionState("PLAY", 3) {
        registerPacket(classOf[SPacketBlockChange])
        registerPacket( classOf[SPacketChunkData])
        registerPacket( classOf[SPacketHeldItemChange])
        registerPacket( classOf[SPacketJoinGame])
        registerPacket( classOf[SPacketPlayerPosLook])
        registerPacket( classOf[SPacketSpawnPosition])
        registerPacket( classOf[SPacketUnloadChunk])
        registerPacket( classOf[SPacketMultiBlockChange])
        registerPacket( classOf[SPacketSetSlot])
        registerPacket( classOf[SPacketWindowItems])
        registerPacket( classOf[SPacketChangeGameState])


        registerPacket( classOf[CPacketHeldItemChange])
        registerPacket( classOf[CPacketPlayer])
        registerPacket( classOf[CPacketPlayer.Position])
        registerPacket( classOf[CPacketPlayer.Rotation])
        registerPacket( classOf[CPacketPlayer.PositionRotation])
       // registerPacket( classOf[CPacketPlayerDigging])
        //registerPacket( classOf[CPacketPlayerTryUseItem])
      //  registerPacket( classOf[CPacketPlayerTryUseItemOnBlock])
        registerPacket( classOf[CPacketClickWindow])
        registerPacket( classOf[CPacketNEI])

        registerPacket( classOf[CPacketPlayerMouse])
        registerPacket( classOf[CPacketCloseWindow])



    }

    val STATES_BY_ID = Array(HANDSHAKING, STATUS, LOGIN, PLAY)
    addClass(HANDSHAKING)
    addClass(STATUS)
    addClass(LOGIN)
    addClass(PLAY)

    def addClass(state: ConnectionState): Unit = {

        state.packetsId.keySet.foreach(packet => STATES_BY_CLASS += packet -> state)


    }


}

