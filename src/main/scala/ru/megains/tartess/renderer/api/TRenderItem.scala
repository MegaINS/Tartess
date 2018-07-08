package ru.megains.tartess.renderer.api

import ru.megains.tartess.renderer.mesh.Mesh

trait  TRenderItem {

    val inventoryMesh: Mesh

    val worldMesh: Mesh

    def renderInInventory(): Unit

    def renderInWorld(): Unit

}
