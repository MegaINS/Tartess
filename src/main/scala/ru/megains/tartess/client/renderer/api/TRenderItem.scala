package ru.megains.tartess.client.renderer.api

import ru.megains.tartess.client.renderer.mesh.Mesh

trait  TRenderItem {

    val inventoryMesh: Mesh

    val worldMesh: Mesh

    def renderInInventory(): Unit

    def renderInWorld(): Unit

}
