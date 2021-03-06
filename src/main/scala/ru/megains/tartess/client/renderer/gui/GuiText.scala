package ru.megains.tartess.client.renderer.gui

import ru.megains.tartess.client.renderer.mesh.Mesh

import scala.collection.mutable

trait GuiText {

    val text: mutable.HashMap[String, Mesh] = mutable.HashMap[String, Mesh]()

    def addText(name: String, mesh: Mesh): Unit = {
        if (text.getOrElse(name, null) != null) {
            text(name).cleanUp()
        }
        text += name -> mesh
    }


}
