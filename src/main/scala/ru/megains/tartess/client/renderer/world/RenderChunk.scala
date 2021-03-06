package ru.megains.tartess.client.renderer.world


import ru.megains.tartess.client.register.GameRegister
import ru.megains.tartess.client.renderer.mesh.{Mesh, MeshMaker}
import ru.megains.tartess.client.renderer.texture.TextureManager
import ru.megains.tartess.common.world.chunk.{Chunk, ChunkVoid}

class RenderChunk(var chunk: Chunk){


    var isReRender: Boolean = true
    var blockRender: Int = 0
    val meshes: Array[Mesh] = new Array[Mesh](2)


    def render(layer: Int) {



         if (!isVoid) {

             if (isReRender) {
                 if (RenderChunk.rend < 5) {
                     if (!chunk.isVoid) {

                        blockRender = 0
                        cleanUp()
                        makeChunk(0)
                     }
                    isReRender = false
                    RenderChunk.rend += 1
                    RenderChunk.chunkUpdate += 1
                }
            }
            renderChunk(layer)
         }

    }
    def isVoid: Boolean = chunk.isInstanceOf[ChunkVoid]
    private def makeChunk(layer: Int) {


        MeshMaker.startMakeTriangles()
        MeshMaker.setTexture(TextureManager.locationBlockTexture)

        val s = chunk.getBlocks

        s.foreach {
            blockState =>
                GameRegister.getBlockRender(blockState.block).render(blockState, chunk.world, chunk.position)
                blockRender += 1
        }

        meshes(layer) = MeshMaker.makeMesh()

    }



    private def renderChunk(layer: Int) {
        if (blockRender != 0) if (meshes(layer) ne null) {
            meshes(layer).render
            /*
                        chunk.chunkTileEntityMap.values.foreach(tileEntity =>
                                GameRegister.getTileEntityRender(tileEntity.getClass).render(tileEntity,world ,textureManager)
                        )
            */
            RenderChunk.chunkRender += 1
        }
    }

    def reRender() {
        isReRender = true
    }

    def cleanUp() {
        for (mesh <- meshes) {
            if (mesh ne null) mesh.cleanUp()
        }
    }

}

object RenderChunk {
    var chunkRender: Int = 0
    var chunkUpdate: Int = 0
    var rend: Int = 0

    def clearRend() {
        rend = 0
    }
}
