package ru.megains.tartess.server.network

import ru.megains.tartess.common.network.packet.{Packet, PacketRead, PacketWrite}
import ru.megains.tartess.server.network.handshake.client.CHandshake
import ru.megains.tartess.server.network.packet.status.client.{CPacketPing, CPacketServerQuery}
import ru.megains.tartess.server.network.packet.status.server.{SPacketPong, SPacketServerInfo}

import scala.collection.mutable

sealed abstract class ConnectionState(val name: String, val id: Int) {


    val serverIdPacket = new mutable.HashMap[ Class[_ <: PacketWrite],Int]()
    val clientPacketId = new mutable.HashMap[Int,Class[_ <: PacketRead[_]]]()


    def registerServerPacket(packet: Class[_ <: PacketWrite]): Unit = {
        serverIdPacket +=  packet -> serverIdPacket.size
    }
    def registerClientPacket(packet: Class[_ <: PacketRead[_]]): Unit = {
        clientPacketId += clientPacketId.size -> packet
    }
    def getServerPacketId(packet: Class[_ <: PacketWrite]): Int = {
        serverIdPacket(packet)
    }


    def getClientPacket(id: Int): PacketRead[_] = {
        val packet = clientPacketId(id)
        if (packet ne null) packet.newInstance() else null.asInstanceOf[PacketRead[_]]
    }

}


object ConnectionState {


    val STATES_BY_CLASS = new mutable.HashMap[Class[_ <: Packet[_]], ConnectionState]()

    def getFromId(id: Int): ConnectionState = {
        STATES_BY_ID(id)
    }


    def getFromPacket(inPacket: Packet[_]): ConnectionState = {
        STATES_BY_CLASS(inPacket.getClass)
    }


    case object HANDSHAKING extends ConnectionState("HANDSHAKING", 0) {
        // registerServerPacket(classOf[SPacketDisconnect])
         registerClientPacket(classOf[CHandshake])
    }


    case object STATUS extends ConnectionState("STATUS", 1) {
        registerServerPacket( classOf[SPacketPong])
        registerServerPacket( classOf[SPacketServerInfo])

        registerClientPacket( classOf[CPacketPing])
        registerClientPacket( classOf[CPacketServerQuery])
        // registerServerPacket(classOf[SPacketDisconnect])
        // registerPacket(PacketDirection.CLIENTBOUND, classOf[SPacketPong])
        //  registerPacket(PacketDirection.CLIENTBOUND, classOf[SPacketServerInfo])

        //  registerPacket(PacketDirection.SERVERBOUND, classOf[CPacketPing])
        //  registerPacket(PacketDirection.SERVERBOUND, classOf[CPacketServerQuery])
    }

    case object LOGIN extends ConnectionState("LOGIN", 2) {
        //registerServerPacket(classOf[SPacketDisconnect])
        // registerServerPacket(classOf[SPacketLoginSuccess])


        // registerClientPacket(classOf[CPacketLoginStart])

    }


    case object PLAY extends ConnectionState("PLAY", 3) {
        {
            //            registerServerPacket(classOf[SPacketDisconnect])
            //            registerServerPacket(classOf[SPacketPlayerInfo])
            //            registerServerPacket(classOf[SPacketLocInfo])
            //            registerServerPacket(classOf[SPacketInventory])
            //            registerServerPacket(classOf[SPacketBody])
            //            registerServerPacket(classOf[SPacketActionReturn])
            //            registerServerPacket(classOf[SPacketStore])
            //            registerServerPacket(classOf[SPacketInvUpdate])
            //            registerServerPacket(classOf[SPacketStartBattle])
            //            registerServerPacket(classOf[SPacketPlayerLocation])
            //            registerServerPacket(classOf[SPacketSlots])
            //            registerServerPacket(classOf[SPacketSlotUpdate])
        }

        {
            //registerClientPacket(classOf[CPacketAction])
        }






    }
    case object BATTLE_SERVER extends ConnectionState("BATTLE_SERVER", 4) {
        //        registerServerPacket(classOf[SPacketDisconnect])
        //        registerServerPacket(classOf[SPacketPlayerInfo])
        //        registerServerPacket(classOf[SPacketLocInfo])
        //        registerServerPacket(classOf[SPacketInventory])
        //        registerServerPacket(classOf[SPacketBody])
        //        registerServerPacket(classOf[SPacketActionReturn])
        //        registerServerPacket(classOf[SPacketStore])
        //        registerServerPacket(classOf[SPacketInvUpdate])
        //
        //
        //        registerClientPacket(classOf[CPacketAction])
        // registerServerPacket(classOf[PCreateBattle])


        //  registerClientPacket(classOf[PBattleStatus])

    }

    val STATES_BY_ID = Array(HANDSHAKING, STATUS, LOGIN, PLAY,BATTLE_SERVER)
    addClass(HANDSHAKING)
    addClass(STATUS)
    addClass(LOGIN)
    addClass(PLAY)
    addClass(BATTLE_SERVER)

    def addClass(state: ConnectionState): Unit = {
        state.clientPacketId.values.foreach(packet =>
            STATES_BY_CLASS += packet -> state)

        state.serverIdPacket.keySet.foreach(packet =>
            STATES_BY_CLASS += packet -> state)
    }


}
