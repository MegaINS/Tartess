package ru.megains.tartess.renderer.world


import ru.megains.tartess.register.GameRegister
import ru.megains.tartess.renderer.mesh.{Mesh, MeshMaker}
import ru.megains.tartess.renderer.texture.TextureManager
import ru.megains.tartess.world.chunk.Chunk

class RenderChunk(var chunk: Chunk, textureManager: TextureManager){


    var isReRender: Boolean = true
    var blockRender: Int = 0
    val meshes: Array[Mesh] = new Array[Mesh](2)


    def render(layer: Int) {
        // if (!isVoid) {
        if (isReRender) {
            if (RenderChunk.rend < 1) {
                // if (!chunk.isVoid) {

                blockRender = 0
                cleanUp()
                makeChunk(0)
                // }
                isReRender = false
                RenderChunk.rend += 1
                RenderChunk.chunkUpdate += 1
            }
        }
        renderChunk(layer)
        //  }

    }

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
            meshes(layer).render(textureManager)
            //            chunk.chunkTileEntityMap.values.foreach(tileEntity =>
            //                    GameRegister.getTileEntityRender(tileEntity.getClass).render(tileEntity,world ,textureManager)
            //            )
            RenderChunk.chunkRender += 1
        }
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
