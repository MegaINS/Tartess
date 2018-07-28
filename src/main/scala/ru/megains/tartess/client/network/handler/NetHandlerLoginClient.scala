package ru.megains.tartess.client.network.handler

import ru.megains.tartess.client.Tartess
import ru.megains.tartess.client.renderer.gui.base.GuiMenu
import ru.megains.tartess.common.network.handler.INetHandlerLoginClient
import ru.megains.tartess.common.network.{ConnectionState, NetworkManager}
import ru.megains.tartess.common.network.packet.login.server.{SPacketDisconnect, SPacketLoginSuccess}

class NetHandlerLoginClient(networkManager: NetworkManager, gameController: Tartess, previousScreen: GuiMenu) extends INetHandlerLoginClient {


    override def handleDisconnect(packetIn: SPacketDisconnect): Unit = {
       // PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, gameController)
    }

    override def onDisconnect(msg: String): Unit = {

    }

    override def handleLoginSuccess(packetIn: SPacketLoginSuccess): Unit = {
        networkManager.setConnectionState(ConnectionState.PLAY)
        val nhpc: NetHandlerPlayClient = new NetHandlerPlayClient(gameController, previousScreen, networkManager)
        networkManager.setNetHandler(nhpc)
    }
    override def disconnect(msg: String): Unit = {

}
}
