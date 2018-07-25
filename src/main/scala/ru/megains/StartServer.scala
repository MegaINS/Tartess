package ru.megains

import scala.reflect.io.Path

object StartServer extends App {

    try {
        val path = Path("Z:/Tartess/Server").toDirectory
        Thread.currentThread.setName("Server")
       // Tartess.tartess = new Tartess(path)
        //Tartess.tartess.run()
    } catch {
        case e:Exception => println(e)
    }
}
