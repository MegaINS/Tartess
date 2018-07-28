package ru.megains.tartess.common

trait PacketProcess {

    def addPacket(packet:()=>Unit): Unit

}
