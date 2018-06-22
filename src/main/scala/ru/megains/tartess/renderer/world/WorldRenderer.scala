package ru.megains.tartess.renderer.world

import java.awt.Color

import org.lwjgl.opengl.GL11.GL_LINES
import ru.megains.old.entity.player.EntityPlayer
import ru.megains.tartess.block.data.{BlockPos, BlockState}
import ru.megains.tartess.renderer.mesh.{Mesh, MeshMaker}
import ru.megains.tartess.world.World
import ru.megains.tartess.world.chunk.Chunk

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class WorldRenderer(val world: World) {



    val renderChunks: mutable.HashMap[Long, RenderChunk] = new mutable.HashMap[Long, RenderChunk]

    val playerRenderChunks:ArrayBuffer[RenderChunk] =  mutable.ArrayBuffer[RenderChunk]()

    var blockMouseOver: Mesh = _


    def getRenderChunks(entityPlayer: EntityPlayer): ArrayBuffer[RenderChunk] = {
//        // TODO:  OPTIMIZE
//        val posX: Int = entityPlayer.posX / 64 - (if (entityPlayer.posX < 0) 1 else 0) toInt
//        val posY: Int = entityPlayer.posY / 64 - (if (entityPlayer.posY < 0) 1 else 0) toInt
//        val posZ: Int = entityPlayer.posZ / 64 - (if (entityPlayer.posZ < 0) 1 else 0) toInt
//
//        if(posX != lastX ||
//                posY != lastY ||
//                posZ != lastZ ||
//                playerRenderChunks.isEmpty){
//            lastX = posX
//            lastY = posY
//            lastZ = posZ
//            playerRenderChunks.clear()
//            for(x <- posX - range to posX + range;
//                y <- posY - range to posY + range;
//                z <- posZ - range to posZ + range){
//                playerRenderChunks += getRenderChunk(x, y, z)
//            }
//        }


        if(playerRenderChunks.isEmpty){
            for(x <- -world.length to world.length;
                y <- -world.height to world.height;
                z <- -world.width to world.width){
                playerRenderChunks += getRenderChunk(x, y, z)
            }
        }
        playerRenderChunks
    }

    def getRenderChunk(x: Int, y: Int, z: Int): RenderChunk = {
        val i: Long = Chunk.getIndex(x, y, z)
        if (renderChunks.contains(i)/* && !renderChunks(i).isVoid*/) renderChunks(i)
        else {
            val chunkRen = createChunkRen(x, y, z)
            renderChunks += i -> chunkRen
            chunkRen
        }
    }

    def createChunkRen(x: Int, y: Int, z: Int): RenderChunk = {
        new RenderChunk(world.getChunk(x,y,z))
    }

    def renderBlockMouseOver(): Unit = if (blockMouseOver != null){
        blockMouseOver.render
    }

    def updateBlockMouseOver(pos: BlockPos, blockState: BlockState): Unit = {
        if (blockMouseOver != null) {
            blockMouseOver.cleanUp()
            blockMouseOver = null
        }

        val mm = MeshMaker.getMeshMaker
        if( blockState == null){
            val aabb = blockState.getSelectedBoundingBox
        }
        val aabb = blockState.getSelectedBoundingBox

        val minX = aabb.minX/8f - 0.01f
        val minY = aabb.minY/8f - 0.01f
        val minZ = aabb.minZ/8f - 0.01f
        val maxX = aabb.maxX/8f + 0.01f
        val maxY = aabb.maxY/8f + 0.01f
        val maxZ = aabb.maxZ/8f + 0.01f



        mm.startMake(GL_LINES)
        mm.addColor(Color.BLACK)
        mm.addVertex(minX, minY, minZ)
        mm.addVertex(minX, minY, maxZ)
        mm.addVertex(minX, maxY, minZ)
        mm.addVertex(minX, maxY, maxZ)
        mm.addVertex(maxX, minY, minZ)
        mm.addVertex(maxX, minY, maxZ)
        mm.addVertex(maxX, maxY, minZ)
        mm.addVertex(maxX, maxY, maxZ)

        mm.addIndex(0, 1)
        mm.addIndex(0, 2)
        mm.addIndex(0, 4)

        mm.addIndex(6, 2)
        mm.addIndex(6, 4)
        mm.addIndex(6, 7)

        mm.addIndex(3, 1)
        mm.addIndex(3, 2)
        mm.addIndex(3, 7)

        mm.addIndex(5, 1)
        mm.addIndex(5, 4)
        mm.addIndex(5, 7)


        blockMouseOver = mm.makeMesh()
    }
}
