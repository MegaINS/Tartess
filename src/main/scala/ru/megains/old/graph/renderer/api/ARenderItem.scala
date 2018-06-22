package ru.megains.old.graph.renderer.api


import ru.megains.old.graph.ShaderProgram
import ru.megains.old.graph.renderer.mesh.Mesh
import ru.megains.tartess.renderer.texture.TextureManager

abstract class ARenderItem {

    val inventoryMesh:Mesh

    val worldMesh:Mesh

    def renderInInventory(shaderProgram: ShaderProgram,textureManager:TextureManager):Unit

    def renderInWorld(shaderProgram: ShaderProgram,textureManager:TextureManager): Unit

}
