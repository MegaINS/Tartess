package ru.megains.tartess.renderer.world

import java.awt.Color

import org.lwjgl.opengl.GL11.GL_LINES
import ru.megains.old.graph.Frustum
import ru.megains.tartess.block.data.{BlockPos, BlockState}
import ru.megains.tartess.entity.item.EntityItem
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.physics.BoundingBoxes
import ru.megains.tartess.register.GameRegister
import ru.megains.tartess.renderer.block.RenderBlock
import ru.megains.tartess.renderer.mesh.{Mesh, MeshMaker}
import ru.megains.tartess.renderer.{Renderer, Transformation}
import ru.megains.tartess.utils.RayTraceResult
import ru.megains.tartess.world.World
import ru.megains.tartess.world.chunk.Chunk

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class WorldRenderer(val world: World) {



    world.worldRenderer = this

    val renderChunks: mutable.HashMap[Long, RenderChunk] = new mutable.HashMap[Long, RenderChunk]

    val playerRenderChunks:ArrayBuffer[RenderChunk] =  mutable.ArrayBuffer[RenderChunk]()

    var blockMouseOver: Mesh = _
    var blockSelect: Mesh = _
    val range = 5
    var lastX = 0
    var lastY = 0
    var lastZ = 0
    var blockStateMouseOver:BlockState = _
    var blockStateSelect:BlockState = _
    var selectPos: Array[Mesh] = new Array[Mesh](6)

    setTest()

    def setTest(): Unit ={
        val zero = 0f
        val neZero = 0.001f
        val one = 1/16f

        MeshMaker.startMakeTriangles()
        RenderBlock.renderSideDown(zero,one,-neZero,zero,one)
        selectPos(0) = MeshMaker.makeMesh()

        MeshMaker.startMakeTriangles()
        RenderBlock.renderSideUp(zero,one,neZero,zero,one)
        selectPos(1) = MeshMaker.makeMesh()


        MeshMaker.startMakeTriangles()
        RenderBlock.renderSideNorth(zero,one,zero,one,-neZero)
        selectPos(2) = MeshMaker.makeMesh()

        MeshMaker.startMakeTriangles()
        RenderBlock.renderSideSouth(zero,one,zero,one,neZero)
        selectPos(3) = MeshMaker.makeMesh()

        MeshMaker.startMakeTriangles()
        RenderBlock.renderSideWest(-neZero,zero,one,zero,one)
        selectPos(4) = MeshMaker.makeMesh()

        MeshMaker.startMakeTriangles()
        RenderBlock.renderSideEast(neZero,zero,one,zero,one)
        selectPos(5) = MeshMaker.makeMesh()


    }

    def renderSelectPos(objectMouseOver: RayTraceResult): Unit = {
        selectPos(objectMouseOver.sideHit.id).render
    }






    def getRenderChunks(entityPlayer: EntityPlayer): ArrayBuffer[RenderChunk] = {
//        // TODO:  OPTIMIZE
        val posX: Int = entityPlayer.posX / 256 - (if (entityPlayer.posX < 0) 1 else 0) toInt
        val posY: Int = entityPlayer.posY / 256 - (if (entityPlayer.posY < 0) 1 else 0) toInt
        val posZ: Int = entityPlayer.posZ / 256 - (if (entityPlayer.posZ < 0) 1 else 0) toInt

        if(posX != lastX ||
                posY != lastY ||
                posZ != lastZ ||
                playerRenderChunks.isEmpty){
            lastX = posX
            lastY = posY
            lastZ = posZ
            playerRenderChunks.clear()
            for(x <- posX - range to posX + range;
                y <- posY - range to posY + range;
                z <- posZ - range to posZ + range){
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

    def renderBlockMouseOver(): Unit = if (blockMouseOver != null) blockMouseOver.render

    def renderBlockSelect(): Unit = if (blockSelect != null) blockSelect.render

    def updateBlockMouseOver(blockState: BlockState): Unit = {
        //TODO
        if(blockStateMouseOver == blockState) return
        if (blockMouseOver != null) {
            blockMouseOver.cleanUp()
            blockMouseOver = null
        }

        val mm = MeshMaker.getMeshMaker
        mm.startMake(GL_LINES)

        val aabbs:BoundingBoxes= blockState.getBoundingBox

        for(aabb<-aabbs.hashSet){
            val minX = aabb.minX/16f - 0.01f
            val minY = aabb.minY/16f - 0.01f
            val minZ = aabb.minZ/16f - 0.01f
            val maxX = aabb.maxX/16f + 0.01f
            val maxY = aabb.maxY/16f + 0.01f
            val maxZ = aabb.maxZ/16f + 0.01f



            mm.setCurrentIndex()
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
        }





        blockMouseOver = mm.makeMesh()
    }




    def updateBlockSelect(blockState: BlockState): Unit = {
        //TODO
        if(blockStateSelect == blockState) return
        if (blockSelect != null) {
            blockSelect.cleanUp()
            blockSelect = null
        }

        val mm = MeshMaker.getMeshMaker
        mm.startMake(GL_LINES)


        val aabbs:BoundingBoxes= blockState.getBoundingBox

        for(aabb<-aabbs.hashSet) {
            val minX = aabb.minX / 16f
            val minY = aabb.minY / 16f
            val minZ = aabb.minZ / 16f
            val maxX = aabb.maxX / 16f
            val maxY = aabb.maxY / 16f
            val maxZ = aabb.maxZ / 16f


            mm.setCurrentIndex()
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

        }
        blockSelect = mm.makeMesh()
    }

    def reRender(pos: BlockPos) {
        //TODO
        val x: Int = pos.x >> 8
        val y: Int = pos.y >> 8
        val z: Int = pos.z >> 8
        getRenderChunk(x, y, z).reRender()
        getRenderChunk(x + 1, y, z).reRender()
        getRenderChunk(x - 1, y, z).reRender()
        getRenderChunk(x, y + 1, z).reRender()
        getRenderChunk(x, y - 1, z).reRender()
        getRenderChunk(x, y, z + 1).reRender()
        getRenderChunk(x, y, z - 1).reRender()
    }

    def reRenderWorld(): Unit = {
        renderChunks.values.foreach(_.reRender())
    }


    def renderEntitiesItem(frustum: Frustum, transformation: Transformation): Unit = {


        world.entities.filter(_.isInstanceOf[EntityItem]).splitter.foreach(
            entity => {
              //  if (frustum.cubeInFrustum(entity.body)) {
                    val modelViewMatrix = transformation.buildEntityItemModelViewMatrix(entity.asInstanceOf[EntityItem])
                    Renderer.currentShaderProgram.setUniform("model", modelViewMatrix)
                    GameRegister.getItemRender(entity.asInstanceOf[EntityItem].itemStack.item).renderInWorld()
              //  }
            }
        )
    }
    def renderEntities(frustum: Frustum, transformation: Transformation): Unit = {


        world.entities.filter {
            case _: EntityItem | _: EntityPlayer => false
            case _ => true
        }.splitter.foreach(
            entity => {
               // if (frustum.cubeInFrustum(entity.body)) {
                    val modelViewMatrix = transformation.buildEntityModelViewMatrix(entity)
                    Renderer.currentShaderProgram.setUniform("model", modelViewMatrix)
                    GameRegister.getEntityRender(entity).render(entity,world)
                }
           // }
        )
    }

}
