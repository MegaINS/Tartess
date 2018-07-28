package ru.megains.tartess.server.network.handler

import ru.megains.tartess.common.network.NetworkManager
import ru.megains.tartess.common.network.handler.INetHandlerLoginServer
import ru.megains.tartess.common.network.packet.login.client.CPacketLoginStart
import ru.megains.tartess.common.network.packet.login.server.SPacketLoginSuccess
import ru.megains.tartess.server.TartessServer
import ru.megains.tartess.server.entity.EntityPlayerMP

class NetHandlerLoginServer(server: TartessServer, networkManager: NetworkManager) extends INetHandlerLoginServer /*with ITickable*/ {

    var currentLoginState = LoginState.HELLO
    var name: String = _


    override def processLoginStart(packetIn: CPacketLoginStart): Unit = {
        currentLoginState = LoginState.READY_TO_ACCEPT
        name = packetIn.name
        tryAcceptPlayer()
    }


    override def onDisconnect(msg: String): Unit = {

    }

    def update() {
        if (currentLoginState eq LoginState.READY_TO_ACCEPT) tryAcceptPlayer()

        //        if ( {
        //            connectionTimer += 1; connectionTimer - 1
        //        } == 600) closeConnection("Took too long to log in")
    }

    def tryAcceptPlayer() {
        //  val s: String = server.playerList.allowUserToConnect(networkManager.getRemoteAddress, loginGameProfile)
        //  if (s != null) closeConnection(s)
        //   else {
        currentLoginState = LoginState.ACCEPTED
        networkManager.sendPacket(new SPacketLoginSuccess())

        var entityPlayer: EntityPlayerMP = server.playerList.getPlayerByName(name)
        if (entityPlayer == null) {
            entityPlayer = server.playerList.createPlayerForUser(name)
        }
        server.playerList.initializeConnectionToPlayer(networkManager, entityPlayer)
        // }
    }

    object LoginState extends Enumeration {
        type LoginState = Value
        val HELLO, KEY, AUTHENTICATING, READY_TO_ACCEPT, DELAY_ACCEPT, ACCEPTED = Value
    }

    override def disconnect(msg: String): Unit = {

    }
}


