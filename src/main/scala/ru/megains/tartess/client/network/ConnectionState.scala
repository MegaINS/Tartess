package ru.megains.tartess.client.network

import ru.megains.tartess.client.network.handshake.client.CHandshake
import ru.megains.tartess.client.network.status.client.{CPacketPing, CPacketServerQuery}
import ru.megains.tartess.client.network.status.server.{SPacketPong, SPacketServerInfo}
import ru.megains.tartess.common.network.packet.{Packet, PacketRead, PacketWrite}

import scala.collection.mutable

sealed abstract class ConnectionState(val name: String, val id: Int) {


    val serverIdPacket = new mutable.HashMap[Int, Class[_ <: PacketRead[_]]]()
    val clientPacketId = new mutable.HashMap[Class[_ <: PacketWrite],Int]()


    def registerServerPacket(packet: Class[_ <: PacketRead[_]]): Unit = {
        serverIdPacket += serverIdPacket.size ->  packet
    }
    def registerClientPacket(packet: Class[_ <: PacketWrite]): Unit = {
        clientPacketId += packet -> clientPacketId.size
    }
    def getServerPacket(id:Int): PacketRead[_] = {
        val packet = serverIdPacket(id)
        if (packet ne null) packet.newInstance() else null.asInstanceOf[PacketRead[_]]
    }


    def getClientPacket(id: Class[_ <: PacketWrite]): Int = {
        clientPacketId(id)
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
        state.clientPacketId.keySet.foreach(packet =>
            STATES_BY_CLASS += packet -> state)

        state.serverIdPacket.values.foreach(packet =>
            STATES_BY_CLASS += packet -> state)
    }


}


