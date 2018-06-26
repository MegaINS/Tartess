package ru.megains.tartess.renderer.item


import ru.megains.tartess.item.Item
import ru.megains.tartess.renderer.api.RenderItem
import ru.megains.tartess.renderer.mesh.{Mesh, MeshMaker}
import ru.megains.tartess.renderer.texture.{TextureAtlas, TextureManager}

class RenderItemStandart(val item: Item) extends RenderItem {

    override lazy val inventoryMesh: Mesh = createInventoryMesh()

    override lazy val worldMesh: Mesh =  createWorldMesh()

    override def renderInInventory(): Unit = {
        inventoryMesh.render
    }

    override def renderInWorld(): Unit = {
        worldMesh.render
    }

    def createWorldMesh(): Mesh = {

        val mm = MeshMaker.getMeshMaker


        val maxX = 0.5f
        val maxY = 0.5f
        val minX = -0.5f
        val minY = -0.5f
        val minZ = 0.1f
        val maxZ = -0.1f


        var minU: Float = 0
        var maxU: Float = 0
        var minV: Float = 0
        var maxV: Float = 0
        var texture: TextureAtlas = null


        mm.startMakeTriangles()
        mm.setTexture(TextureManager.locationBlockTexture)
        texture = item.aTexture
        minU = texture.minU
        maxU = texture.maxU
        minV = texture.minV
        maxV = texture.maxV

        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, minZ, minU, maxV)
        mm.addVertexWithUV(minX, maxY, minZ, minU, minV)
        mm.addVertexWithUV(maxX, maxY, minZ, maxU, minV)
        mm.addVertexWithUV(maxX, minY, minZ, maxU, maxV)
        mm.addIndex(0, 2, 1)
        mm.addIndex(0, 3, 2)



        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, maxZ, minU, maxV)
        mm.addVertexWithUV(minX, maxY, maxZ, minU, minV)
        mm.addVertexWithUV(maxX, maxY, maxZ, maxU, minV)
        mm.addVertexWithUV(maxX, minY, maxZ, maxU, maxV)
        mm.addIndex(0, 1, 2)
        mm.addIndex(0, 2, 3)





        mm.makeMesh()
    }

    def createInventoryMesh(): Mesh = {
        val mm = MeshMaker.getMeshMaker


        val maxX = 32
        val maxY = 32
        val minX = 0
        val minY = 0
        val zZero = 0.0f


        var minU: Float = 0
        var maxU: Float = 0
        var minV: Float = 0
        var maxV: Float = 0
        var texture: TextureAtlas = null


        mm.startMakeTriangles()
        mm.setTexture(TextureManager.locationBlockTexture)
        texture = item.aTexture
        minU = texture.minU
        maxU = texture.maxU
        minV = texture.minV
        maxV = texture.maxV

        mm.setCurrentIndex()
        mm.addVertexWithUV(minX, minY, zZero, minU, maxV)
        mm.addVertexWithUV(minX, maxY, zZero, minU, minV)
        mm.addVertexWithUV(maxX, maxY, zZero, maxU, minV)
        mm.addVertexWithUV(maxX, minY, zZero, maxU, maxV)
        mm.addIndex(0, 2, 1)
        mm.addIndex(0, 3, 2)

        mm.makeMesh()


    }

}
