package ru.megains.tartess.client.renderer

import java.nio.FloatBuffer

import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11._
import ru.megains.old.graph.Frustum
import ru.megains.tartess.client.{Options, Tartess}
import ru.megains.tartess.client.periphery.Mouse
import ru.megains.tartess.client.renderer.light.DirLight
import ru.megains.tartess.client.renderer.shader.{GuiShaderProgram, LightShaderProgram, SceneShaderProgram, ShaderProgram}
import ru.megains.tartess.client.renderer.texture.TextureManager
import ru.megains.tartess.client.renderer.world.{RenderChunk, WorldRenderer}
import ru.megains.tartess.common.utils.{MathHelper, RayTraceType}

class Renderer(val tar: Tartess) {

    Renderer.renderer = this

    val Z_NEAR: Float = 0.01f
    val Z_FAR: Float = 1000f

    val _proj: FloatBuffer = BufferUtils.createFloatBuffer(16)
    val _modl: FloatBuffer = BufferUtils.createFloatBuffer(16)

    var frustum: Frustum = _
    val transformation = new Transformation
    var worldRenderer: WorldRenderer = _
    var sceneShaderProgram:ShaderProgram = new SceneShaderProgram
    val sceneLightShaderProgram = new LightShaderProgram
    var guiShaderProgram = new GuiShaderProgram
    val dirLight = new DirLight
    var isLight = true


    def init() {
        setupSceneShader()
        setupGuiShader()
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        glEnable(GL_STENCIL_TEST)
        glEnable(GL_DEPTH_TEST)

        dirLight.direction.set(-0.2f,-1f,-0.3f)
        dirLight.ambient.set(1f,0.8f,0.5f)
        dirLight.diffuse.set(0)
        dirLight.specular.set(0)
        dirLight.shininess = 32

/*
        val mm = MeshMaker

        mm.startMakeTriangles()


        mm.setCurrentIndex()
        mm.addColor(Color.ORANGE)
        mm.addVertex(0, 0, -20)
        mm.addVertex(0, 16, -20)
        mm.addVertex(16, 0, -20)
        mm.addVertex(16, 16, -20)
        mm.addVertex(8, 8, -20)
        mm.addIndex(1, 0, 4)
        mm.addIndex(4, 0, 2)
        mm.addIndex(4, 2, 3)
        mm.addIndex(3, 1, 4)
        mesh = mm.makeMesh()
*/
    }

    def setupSceneShader():Unit = {
        sceneLightShaderProgram.create()
        sceneShaderProgram.create()
    }

    private def setupGuiShader() {
        guiShaderProgram.create()
    }

    def render(camera: Camera) {

        val projectionMatrix: Matrix4f = transformation.updateProjectionMatrix(Options.FOV,  tar.window.width, tar.window.height, Z_NEAR, Z_FAR)
        val viewMatrix: Matrix4f = transformation.updateViewMatrix(camera)

        projectionMatrix.get(_proj.clear().asInstanceOf[FloatBuffer])
        viewMatrix.get(_modl.clear().asInstanceOf[FloatBuffer])

        frustum = Frustum.getFrustum(_proj, _modl)

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT)
        if (tar.world != null) renderScene()
        renderGui()
    }

    private def renderScene() {

        if(isLight){
            Renderer.bindShaderProgram(sceneLightShaderProgram)
        }else{
            Renderer.bindShaderProgram(sceneShaderProgram)
        }

        Renderer.setUniforms()



        glEnable(GL_CULL_FACE)

        RenderChunk.clearRend()

/*
        sceneShaderProgram.setUniform("model",  transformation.buildChunkModelViewMatrix(new ChunkPosition(0,0,0)))
        mesh.render



        sceneShaderProgram.setUniform("model",  transformation.buildChunkModelViewMatrix(new ChunkPosition(0,0,1)))
        mesh.render
        */
        worldRenderer.getRenderChunks(tar.player).foreach((renderChunk: RenderChunk) => {
            if(frustum.chunkInFrustum(renderChunk.chunk.position)){
                Renderer.currentShaderProgram.setUniform("model", transformation.buildChunkModelViewMatrix(renderChunk.chunk.position))
                renderChunk.render(0)
            }
        })

        worldRenderer.renderEntitiesItem(frustum, transformation)
        worldRenderer.renderEntities(frustum, transformation)
        glDisable(GL_CULL_FACE)


        TextureManager.tm.unbindTexture()
        val blockSelectPosition = tar.blockSelectPosition
        if (blockSelectPosition != null) {
            Renderer.currentShaderProgram.setUniform("model", transformation.buildObjectMouseOverViewMatrix(blockSelectPosition.pos))
            worldRenderer.renderBlockSelect()
        }else{
            val objectMouseOver = tar.objectMouseOver
            if (objectMouseOver.rayTraceType == RayTraceType.BLOCK) {
                Renderer.currentShaderProgram.setUniform("model", transformation.buildObjectMouseOverViewMatrix(objectMouseOver.blockPos))
                worldRenderer.renderBlockMouseOver()
            }
        }
        val objectMouseOver = tar.objectMouseOver
        if(objectMouseOver.rayTraceType == RayTraceType.BLOCK){
            val ve3f =objectMouseOver.hitVec
            Renderer.currentShaderProgram.setUniform("model", transformation.buildViewMatrix(
                MathHelper.floor_double( objectMouseOver.blockPos.x + (ve3f.x-objectMouseOver.blockPos.x)),
                MathHelper.floor_double( objectMouseOver.blockPos.y + (ve3f.y-objectMouseOver.blockPos.y)),
                MathHelper.floor_double( objectMouseOver.blockPos.z + (ve3f.z-objectMouseOver.blockPos.z)))
            )
            worldRenderer.renderSelectPos(tar.objectMouseOver)
        }

        Renderer.unbindShaderProgram()
    }

    private def renderGui() {
        glEnable(GL_BLEND)
        glEnable(GL_CULL_FACE)
        glDisable(GL_DEPTH_TEST)

        Renderer.bindShaderProgram(guiShaderProgram)



        Renderer.setUniforms()

        tar.guiManager.draw(Mouse.getX, Mouse.getY)


        Renderer.unbindShaderProgram()


        glEnable(GL_DEPTH_TEST)
        glDisable(GL_BLEND)
        glDisable(GL_CULL_FACE)
    }

}

object Renderer {

    var currentShaderProgram: ShaderProgram = _
    var renderer:Renderer = _

    def bindShaderProgram(shaderProgram: ShaderProgram): Unit = {
        if (currentShaderProgram ne null) currentShaderProgram.unbind()
        currentShaderProgram = shaderProgram
        currentShaderProgram.bind()
    }

    def setUniforms(): Unit ={
        currentShaderProgram.setUniforms(renderer)
    }

    def unbindShaderProgram(): Unit = {
        if (currentShaderProgram ne null) currentShaderProgram.unbind()
        currentShaderProgram = null
    }
}
