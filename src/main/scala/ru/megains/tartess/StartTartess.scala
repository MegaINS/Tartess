package ru.megains.tartess

object StartTartess extends App {

    try {
        Thread.currentThread.setName("Client")
        Tartess.tartess = new Tartess()
        Tartess.tartess.run()
    } catch {
        case e:Exception => println(e)
    }
}
