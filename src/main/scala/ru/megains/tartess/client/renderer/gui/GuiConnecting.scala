package ru.megains.tartess.client.renderer.gui

import java.net.{InetAddress, UnknownHostException}

import ru.megains.tartess.client.Tartess
import ru.megains.tartess.client.network.{ServerAddress, ServerData}
import ru.megains.tartess.client.network.handler.NetHandlerLoginClient
import ru.megains.tartess.client.renderer.gui.base.GuiMenu
import ru.megains.tartess.client.renderer.gui.element.GuiButton
import ru.megains.tartess.common.network.packet.login.client.CPacketLoginStart
import ru.megains.tartess.common.network.{ConnectionState, NetworkManager}
import ru.megains.tartess.common.network.packet.handshake.client.CHandshake
import ru.megains.tartess.common.utils.Logger


class GuiConnecting(parent: GuiMenu, oc: Tartess, serverDataIn: ServerData) extends GuiMenu with Logger[GuiConnecting] {


    val serveraddress: ServerAddress = new ServerAddress(serverDataIn.serverIP, 20000)
    var networkManager: NetworkManager = _
    var cancel = false
    var error: GuiMenu = _
    oc.loadWorld(null)
     // mcIn.setServerData(serverDataIn)
    connect(serveraddress.getIP, serveraddress.getPort)


    override def initGui(): Unit = {
        buttonList += new GuiButton(0, oc, "Cancel", 300, 200, 200, 50)
    }

    private def connect(ip: String, port: Int) {
        log.info("Connecting to {}, {}", Array[AnyRef](ip, Integer.valueOf(port)))
        new Thread("Server Connector #" /* + CONNECTION_ID.incrementAndGet*/) {
            override def run() {
                var inetaddress: InetAddress = null
                try {
                    if (cancel) {
                        return
                    }
                    inetaddress = InetAddress.getByName(ip)
                    networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, port,tar)
                    networkManager.setNetHandler(new NetHandlerLoginClient(networkManager, oc, parent))
                    networkManager.sendPacket(new CHandshake(210, ip, port, ConnectionState.LOGIN))
                    networkManager.sendPacket(new CPacketLoginStart(oc.playerName))
                }
                catch {
                    case unknownhostexception: UnknownHostException => {
                        if (cancel) {
                            return
                        }
                        log.error("Couldn\'t connect to server", unknownhostexception.asInstanceOf[Throwable])

                        error = new GuiDisconnected(parent, "connect.failed", "Unknown host")
                    }
                    case exception: Exception => {
                        if (cancel) {
                            return
                        }
                        log.error("Couldn\'t connect to server", exception.asInstanceOf[Throwable])
                        var s: String = exception.toString
                        if (inetaddress != null) {
                            val s1: String = inetaddress + ":" + port
                            s = s.replaceAll(s1, "")
                        }
                        error = new GuiDisconnected(parent, "connect.failed", s)
                    }
                }
            }
        }.start()
    }

    override def actionPerformed(button: GuiButton): Unit = {
        button.id match {
            case 0 => oc.guiManager.setGuiScreen(parent)
            case _ =>
        }
    }

    override def tick(): Unit = {
        if (error != null) {
            oc.guiManager.setGuiScreen(error)
        }
    }

}
