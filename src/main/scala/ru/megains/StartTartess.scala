package ru.megains

import ru.megains.tartess.Tartess

import scala.reflect.io.Path

object StartTartess extends App {

    try {
        val path = Path("Z:/Tartess/Client").toDirectory
        Thread.currentThread.setName("Client")
        Tartess.tartess = new Tartess(path)
        Tartess.tartess.run()
    } catch {
        case e:Exception => println(e)
    }
}
