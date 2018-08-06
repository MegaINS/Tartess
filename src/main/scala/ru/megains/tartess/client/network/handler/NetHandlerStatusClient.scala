package ru.megains.tartess.client.network.handler

import ru.megains.tartess.client.Tartess
import ru.megains.tartess.client.network.ServerData
import ru.megains.tartess.common.network.NetworkManager
import ru.megains.tartess.common.network.handler.INetHandlerStatusClient
import ru.megains.tartess.common.network.packet.status.client.CPacketPing
import ru.megains.tartess.common.network.packet.status.server.{SPacketPong, SPacketServerInfo}
import ru.megains.tartess.common.utils.Logger

class NetHandlerStatusClient(server:ServerData,networkManager: NetworkManager) extends INetHandlerStatusClient with Logger[NetHandlerStatusClient]{

    var successful: Boolean = false
    var receivedStatus: Boolean = false
    var pingSentAt: Long = 0L

    override def handleServerInfo(packetIn: SPacketServerInfo) {
        if (receivedStatus) networkManager.closeChannel("ServerPinger" + "receivedStatus")
        else {
            receivedStatus = true

            this.pingSentAt = Tartess.getSystemTime
            networkManager.sendPacket(new CPacketPing(pingSentAt))
            this.successful = true
        }
    }

    override def handlePong(packetIn: SPacketPong) {
        val i: Long = pingSentAt
        val j: Long = Tartess.getSystemTime
        server.pingToServer = j - i
        networkManager.closeChannel("ServerPinger" + "handlePong")
    }

    override def onDisconnect(msg: String) {
        if (!successful) {
            log.error("Can\'t ping {}: {}", Array[AnyRef](server.serverIP, msg))
            server.serverMOTD = "Can\'t connect to server."
            server.populationInfo = ""
                    }
    }

    override def disconnect(msg: String): Unit = {

    }
}
