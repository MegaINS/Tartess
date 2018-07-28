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
            //                    val serverstatusresponse: ServerStatusResponse = packetIn.getResponse
            //                    if (serverstatusresponse.getServerDescription != null) server.serverMOTD = serverstatusresponse.getServerDescription
            //                    else server.serverMOTD = ""
            //                    if (serverstatusresponse.getVersion != null) {
            //                        server.gameVersion = serverstatusresponse.getVersion.getName
            //                        server.version = serverstatusresponse.getVersion.getProtocol
            //                    }
            //                    else {
            //                        server.gameVersion = "Old"
            //                        server.version = 0
            //                    }
            //                    if (serverstatusresponse.getPlayers != null) {
            //                        server.populationInfo =  "" + serverstatusresponse.getPlayers.getOnlinePlayerCount + "" + "/" + serverstatusresponse.getPlayers.getMaxPlayers
            //                        if (ArrayUtils.isNotEmpty(serverstatusresponse.getPlayers.getPlayers)) {
            //                            val stringbuilder: StringBuilder = new StringBuilder
            //                            for (gameprofile <- serverstatusresponse.getPlayers.getPlayers) {
            //                                if (stringbuilder.nonEmpty) stringbuilder.append("\n")
            //                                stringbuilder.append(gameprofile.getName)
            //                            }
            //                            if (serverstatusresponse.getPlayers.getPlayers.length < serverstatusresponse.getPlayers.getOnlinePlayerCount) {
            //                                if (stringbuilder.nonEmpty) stringbuilder.append("\n")
            //                                stringbuilder.append("... and ").append(serverstatusresponse.getPlayers.getOnlinePlayerCount - serverstatusresponse.getPlayers.getPlayers.length).append(" more ...")
            //                            }
            //                            server.playerList = stringbuilder.toString
            //                        }
            //                    }
            // else
            //  server.populationInfo = "???"
            //                    if (serverstatusresponse.getFavicon != null) {
            //                        val s: String = serverstatusresponse.getFavicon
            //                        if (s.startsWith("data:image/png;base64,")) server.setBase64EncodedIconData(s.substring("data:image/png;base64,".length))
            //                        else log.error("Invalid server icon (unknown format)")
            //                    }
            //  else server.setBase64EncodedIconData(null.asInstanceOf[String])
            // net.minecraftforge.fml.client.FMLClientHandler.instance.bindServerListData(server, serverstatusresponse)
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

    /**
      * Invoked when disconnecting, the parameter is a ChatComponent describing the reason for termination
      */
    override def onDisconnect(msg: String) {
        if (!successful) {
            log.error("Can\'t ping {}: {}", Array[AnyRef](server.serverIP, msg))
            server.serverMOTD = "Can\'t connect to server."
            server.populationInfo = ""
            // ServerPinger.tryCompatibilityPing(server)
        }
    }

    override def disconnect(msg: String): Unit = {

    }
}
