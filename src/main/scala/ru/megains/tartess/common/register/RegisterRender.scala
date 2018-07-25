package ru.megains.tartess.common.register

import scala.collection.mutable


trait RegisterRender[T] {

    val idRender: mutable.HashMap[Int, T] = new mutable.HashMap[Int, T]

    def registerRender(id: Int, aRender: T): Unit = {
        idRender += id -> aRender
    }

    def getRender(id: Int): T = idRender(id)
}
