package ru.megains.tartess.server.network

import ru.megains.tartess.common.network.handler.INetHandler
import ru.megains.tartess.server.TartessServer
import ru.megains.tartess.server.network.packet.status.client.{CPacketPing, CPacketServerQuery}
import ru.megains.tartess.server.network.packet.status.server.{SPacketPong, SPacketServerInfo}

class NetHandlerStatusServer(val server: TartessServer, val networkManager: NetworkManager) extends INetHandler {
    private var handled: Boolean = false

    /**
      * Invoked when disconnecting, the parameter is a ChatComponent describing the reason for termination
      */
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

        this.networkManager.sendPacket(new SPacketPong(packetIn.getClientTime))
        this.networkManager.closeChannel(NetHandlerStatusServer.EXIT_MESSAGE)
    }

    override def disconnect(msg: String): Unit = {

    }
}

object NetHandlerStatusServer {

    private val EXIT_MESSAGE: String = "Status request has been handled."
}
