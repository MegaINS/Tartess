package ru.megains.tartess.client.renderer.gui

import java.awt.Color

import ru.megains.tartess.client.network.{ServerData, ServerPinger}
import ru.megains.tartess.client.renderer.gui.base.GuiMenu
import ru.megains.tartess.client.renderer.gui.element.GuiButton
import ru.megains.tartess.client.renderer.mesh.Mesh

class GuiMultiPlayer(guiMainMenu: GuiMenu) extends GuiMenu {

    var worldsSlot: GuiSlotWorld = _

    val server: ServerData = new ServerData("localhost", "localhost", true)
    val pinger = new ServerPinger
    var pin: Long = -1
    var pingMesh: Mesh = _

    override def initGui(): Unit = {


        worldsSlot = new GuiSlotWorld(0, "World", tar)


        buttonList += new GuiButton(0, tar, "Ping", 500, 100, 200, 50)
        buttonList += new GuiButton(1, tar, "Cancel", 500, 20, 200, 50)
        buttonList += new GuiButton(2, tar, "Connect", 100, 20, 200, 50)


    }

    override def actionPerformed(button: GuiButton): Unit = {
        button.id match {
            case 0 => ping()
            case 1 => tar.guiManager.setGuiScreen(guiMainMenu)
            case 2 => connectToServer(server)
            case _ =>
        }
    }

    override def tick(): Unit = {
        if (pin != server.pingToServer) {
            pin = server.pingToServer
            if (pingMesh != null) pingMesh.cleanUp()
            pingMesh = createString(pin toString, Color.BLACK)
        }
    }

    def connectToServer(server: ServerData) {
        tar.guiManager.setGuiScreen(new GuiConnecting(this, tar, server))
    }

    def ping(): Unit = {
        try {
            pinger.ping(server)
        } catch {
            case e: Throwable =>
                e.printStackTrace()

        }
    }


    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        super.drawScreen(mouseX, mouseY)
        worldsSlot.draw(mouseX, mouseY)
        drawObject(pingMesh, 400, 400)
    }
}
