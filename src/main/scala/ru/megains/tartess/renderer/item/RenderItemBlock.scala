package ru.megains.tartess.renderer.item

import ru.megains.tartess.block.Block
import ru.megains.tartess.block.data.{BlockDirection, BlockPos, BlockState}
import ru.megains.tartess.item.ItemBlock
import ru.megains.tartess.renderer.api.TRenderItem
import ru.megains.tartess.renderer.mesh.{Mesh, MeshMaker}
import ru.megains.tartess.renderer.texture.{TextureAtlas, TextureManager}

class RenderItemBlock(item: ItemBlock) extends TRenderItem {

    override lazy val inventoryMesh: Mesh = createInventoryMesh()
    override lazy val worldMesh: Mesh = createWorldMesh()
    val block: Block = item.block

    override def renderInWorld(): Unit = worldMesh.render

    override def renderInInventory(): Unit = inventoryMesh.render

    def createWorldMesh(): Mesh = {

        createInventoryMesh()
    }

    def createInventoryMesh(): Mesh = {



        val mm = MeshMaker.getMeshMaker

        mm.startMakeTriangles()
        mm.setTexture(TextureManager.locationBlockTexture)

        val aabbs = new BlockState(block,new BlockPos(0,0,0),BlockDirection.NORTH).getBlockBody.div(16)

        for(aabb<- aabbs.hashSet){

            val maxX: Double = aabb.maxX
            val maxY: Double = aabb.maxY
            val maxZ: Double = aabb.maxZ
            val minX: Double = aabb.minX
            val minY: Double = aabb.minY
            val minZ: Double = aabb.minZ


            val averageX = (minX + maxX) / 2
            val averageY = (minY + maxY) / 2
            val averageZ = (minZ + maxZ) / 2


            var minU: Float = 0
            var maxU: Float = 0
            var minV: Float = 0
            var maxV: Float = 0
            var averageU: Float = 0
            var averageV: Float = 0
            var texture: TextureAtlas = null


            texture = block.getATexture(blockDirection = BlockDirection.UP)
            minU = texture.minU
            maxU = texture.maxU
            minV = texture.minV
            maxV = texture.maxV
            averageU = texture.averageU
            averageV = texture.averageV

            mm.setCurrentIndex()
            mm.addVertexWithUV(minX, maxY, minZ, maxU, maxV)
            mm.addVertexWithUV(minX, maxY, maxZ, maxU, minV)
            mm.addVertexWithUV(maxX, maxY, minZ, minU, maxV)
            mm.addVertexWithUV(maxX, maxY, maxZ, minU, minV)
            mm.addVertexWithUV(averageX, maxY, averageZ, averageU, averageV)
            mm.addIndex(0, 1, 4)
            mm.addIndex(3, 2, 4)
            mm.addIndex(1, 3, 4)
            mm.addIndex(2, 0, 4)



            //V2


            texture = block.getATexture(blockDirection =BlockDirection.DOWN)
            minU = texture.minU
            maxU = texture.maxU
            minV = texture.minV
            maxV = texture.maxV
            averageU = texture.averageU
            averageV = texture.averageV





            mm.setCurrentIndex()
            mm.addVertexWithUV(minX, minY, minZ, minU, maxV)
            mm.addVertexWithUV(minX, minY, maxZ, minU, minV)
            mm.addVertexWithUV(maxX, minY, minZ, maxU, maxV)
            mm.addVertexWithUV(maxX, minY, maxZ, maxU, minV)
            mm.addVertexWithUV(averageX, minY, averageZ, averageU, averageV)
            mm.addIndex(1, 0, 4)
            mm.addIndex(2, 3, 4)
            mm.addIndex(3, 1, 4)
            mm.addIndex(0, 2, 4)




            texture = block.getATexture(blockDirection =BlockDirection.NORTH)
            minU = texture.minU
            maxU = texture.maxU
            minV = texture.minV
            maxV = texture.maxV
            averageU = texture.averageU
            averageV = texture.averageV




            mm.setCurrentIndex()

            mm.addVertexWithUV(minX, minY, minZ, minU, maxV)
            mm.addVertexWithUV(minX, minY, maxZ, maxU, maxV)
            mm.addVertexWithUV(minX, maxY, minZ, minU, minV)
            mm.addVertexWithUV(minX, maxY, maxZ, maxU, minV)
            mm.addVertexWithUV(minX, averageY, averageZ, averageU, averageV)
            mm.addIndex(0, 1, 4)
            mm.addIndex(1, 3, 4)
            mm.addIndex(3, 2, 4)
            mm.addIndex(2, 0, 4)



            texture = block.getATexture(blockDirection =BlockDirection.SOUTH)
            minU = texture.minU
            maxU = texture.maxU
            minV = texture.minV
            maxV = texture.maxV
            averageU = texture.averageU
            averageV = texture.averageV



            mm.setCurrentIndex()
            mm.addColor(0.5f, 0.5f, 0.5f)

            mm.addVertexWithUV(maxX, minY, minZ, maxU, maxV)
            mm.addVertexWithUV(maxX, minY, maxZ, minU, maxV)
            mm.addVertexWithUV(maxX, maxY, minZ, maxU, minV)
            mm.addVertexWithUV(maxX, maxY, maxZ, minU, minV)
            mm.addVertexWithUV(maxX, averageY, averageZ, averageU, averageV)
            mm.addIndex(1, 0, 4)
            mm.addIndex(3, 1, 4)
            mm.addIndex(2, 3, 4)
            mm.addIndex(0, 2, 4)






            texture = block.getATexture(blockDirection =BlockDirection.WEST)
            minU = texture.minU
            maxU = texture.maxU
            minV = texture.minV
            maxV = texture.maxV
            averageU = texture.averageU
            averageV = texture.averageV





            mm.setCurrentIndex()
            mm.addColor(0.7f, 0.7f, 0.7f)
            mm.addVertexWithUV(minX, minY, maxZ, minU, maxV)
            mm.addVertexWithUV(minX, maxY, maxZ, minU, minV)
            mm.addVertexWithUV(maxX, minY, maxZ, maxU, maxV)
            mm.addVertexWithUV(maxX, maxY, maxZ, maxU, minV)
            mm.addVertexWithUV(averageX, averageY, maxZ, averageU, averageV)
            mm.addIndex(1, 0, 4)
            mm.addIndex(4, 0, 2)
            mm.addIndex(4, 2, 3)
            mm.addIndex(3, 1, 4)





            texture = block.getATexture(blockDirection =BlockDirection.EAST)
            minU = texture.minU
            maxU = texture.maxU
            minV = texture.minV
            maxV = texture.maxV
            averageU = texture.averageU
            averageV = texture.averageV



            mm.setCurrentIndex()
            mm.addColor(1f, 1f, 1f)
            mm.addVertexWithUV(minX, minY, minZ, maxU, maxV)
            mm.addVertexWithUV(minX, maxY, minZ, maxU, minV)
            mm.addVertexWithUV(maxX, minY, minZ, minU, maxV)
            mm.addVertexWithUV(maxX, maxY, minZ, minU, minV)
            mm.addVertexWithUV(averageX, averageY, minZ, averageU, averageV)
            mm.addIndex(0, 1, 4)
            mm.addIndex(0, 4, 2)
            mm.addIndex(2, 4, 3)
            mm.addIndex(1, 3, 4)


        }









        mm.makeMesh()
    }
}
