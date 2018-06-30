package ru.megains

import ru.megains.old.TartessClient

import scala.reflect.io.Path


object StartClient extends App {

    try {
    Thread.currentThread.setName("Client")
    TartessClient.tartess = new TartessClient(Path("Z:/Tartess/Client").toDirectory)
    TartessClient.tartess.run()
    } catch {
        case e:Exception => println(e)
    }
}
