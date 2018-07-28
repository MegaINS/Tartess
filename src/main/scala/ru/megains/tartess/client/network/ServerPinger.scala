package ru.megains.tartess.client.network

import java.net.{InetAddress, UnknownHostException}

import ru.megains.tartess.client.Tartess
import ru.megains.tartess.client.network.handler.NetHandlerStatusClient
import ru.megains.tartess.common.network.{ConnectionState, NetworkManager}
import ru.megains.tartess.common.network.packet.handshake.client.CHandshake
import ru.megains.tartess.common.network.packet.status.client.CPacketServerQuery
import ru.megains.tartess.common.utils.Logger


class ServerPinger extends Logger[ServerPinger] {

    //  private val pingDestinations: util.List[NetworkManager] = Collections.synchronizedList[NetworkManager](Lists.newArrayList[NetworkManager])

    @throws[UnknownHostException]
    def ping(server: ServerData) {
        val serveraddress: ServerAddress = new ServerAddress(server.serverIP, 20000)
        // val networkmanager: NetworkManager = NetworkManager.createLocalClient(LocalAddress.ANY)
        val networkmanager: NetworkManager = NetworkManager.createNetworkManagerAndConnect(InetAddress.getByName(serveraddress.getIP), serveraddress.getPort,Tartess.tartess)
        // pingDestinations.add(networkmanager)
        server.serverMOTD = "Pinging..."
        server.pingToServer = -1L
        server.playerList = null
        networkmanager.setNetHandler(new NetHandlerStatusClient(server,networkmanager) )
        try {
            networkmanager.sendPacket(new CHandshake(210, serveraddress.getIP, serveraddress.getPort, ConnectionState.STATUS))
            networkmanager.sendPacket(new CPacketServerQuery)

        } catch {
            case throwable: Throwable =>
                log.error(throwable)
                throwable.printStackTrace()
        }
    }
}
