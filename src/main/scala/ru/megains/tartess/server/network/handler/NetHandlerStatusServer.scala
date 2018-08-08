package ru.megains.tartess.server.network.handler

import ru.megains.tartess.common.network.NetworkManager
import ru.megains.tartess.common.network.handler.INetHandlerStatusServer
import ru.megains.tartess.common.network.packet.status.client.{CPacketPing, CPacketServerQuery}
import ru.megains.tartess.common.network.packet.status.server.{SPacketPong, SPacketServerInfo}
import ru.megains.tartess.server.TartessServer

class NetHandlerStatusServer(val server: TartessServer, val networkManager: NetworkManager) extends INetHandlerStatusServer {
    private var handled: Boolean = false


    def onDisconnect(reason: String) {
    }

    def processServerQuery(packetIn: CPacketServerQuery) {

        if (handled) this.networkManager.closeChannel(NetHandlerStatusServer.EXIT_MESSAGE)
        else {
            handled = true
            networkManager.sendPacket(new SPacketServerInfo(server.getServerStatusResponse))
        }
    }

    def processPing(packetIn: CPacketPing) {

        this.networkManager.sendPacket(new SPacketPong(packetIn.clientTime))
        this.networkManager.closeChannel(NetHandlerStatusServer.EXIT_MESSAGE)
    }

    override def disconnect(msg: String): Unit = {

    }
}

object NetHandlerStatusServer {

    private val EXIT_MESSAGE: String = "Status request has been handled."
}
