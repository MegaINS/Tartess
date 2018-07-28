package ru.megains

import ru.megains.tartess.server.TartessServer

import scala.reflect.io.Path

object StartServer extends App {

    try {
        val path = Path("Z:/Tartess/Server").toDirectory
        Thread.currentThread.setName("Server")
        val server = new TartessServer(path)
        server.run()
    } catch {
        case e:Exception => e.printStackTrace()
    }
}
