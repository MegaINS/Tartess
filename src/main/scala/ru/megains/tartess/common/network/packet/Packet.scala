package ru.megains.tartess.common.network.packet

import ru.megains.tartess.common.network.handler.INetHandler

abstract class Packet[T <: INetHandler] {

    def isImportant = false

}



