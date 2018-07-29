package ru.megains

import ru.megains.tartess.server.{ServerCommand, TartessServer}

import scala.reflect.io.Path

object StartServer extends App {

    try {
        val path = Path("Z:/Tartess/Server").toDirectory
        Thread.currentThread.setName("Server")
        val server = new TartessServer(path)
        val serverCommand = new ServerCommand(server)
        serverCommand.setName("serverControl")
        serverCommand.setDaemon(true)
        serverCommand.start()

        server.run()
    } catch {
        case e:Exception => e.printStackTrace()
    }
}
