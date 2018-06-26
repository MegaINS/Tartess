package ru.megains.tartess.renderer.api

import ru.megains.tartess.renderer.mesh.Mesh

abstract class RenderItem {

    val inventoryMesh: Mesh

    val worldMesh: Mesh

    def renderInInventory(): Unit

    def renderInWorld(): Unit

}
