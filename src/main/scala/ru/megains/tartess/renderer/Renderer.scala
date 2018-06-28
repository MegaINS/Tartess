package ru.megains.tartess.renderer

import java.awt.Color
import java.nio.FloatBuffer

import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11._
import ru.megains.old.graph.Frustum
import ru.megains.old.utils.Utils
import ru.megains.tartess.Tartess
import ru.megains.tartess.periphery.Mouse
import ru.megains.tartess.renderer.mesh.{Mesh, MeshMaker}
import ru.megains.tartess.renderer.shader.ShaderProgram
import ru.megains.tartess.renderer.world.{RenderChunk, WorldRenderer}
import ru.megains.tartess.utils.Vec3f
import ru.megains.tartess.world.chunk.ChunkPosition

class Renderer(tar: Tartess) {


    val FOV: Float = Math.toRadians(60.0f).toFloat
    val Z_NEAR: Float = 0.01f
    val Z_FAR: Float = 1000f

    val _proj: FloatBuffer = BufferUtils.createFloatBuffer(16)
    val _modl: FloatBuffer = BufferUtils.createFloatBuffer(16)

    var frustum: Frustum = _
    val transformation = new Transformation
    var worldRenderer: WorldRenderer = _
    val sceneShaderProgram = new ShaderProgram
    var guiShaderProgram = new ShaderProgram
    var mesh:Mesh = _


    def init() {
        setupSceneShader()
        setupGuiShader()
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        glEnable(GL_STENCIL_TEST)
        glEnable(GL_DEPTH_TEST)

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

    }

    def setupSceneShader():Unit = {
        sceneShaderProgram.createVertexShader(Utils.loadResource("/shaders/vertex.vs"))
        sceneShaderProgram.createFragmentShader(Utils.loadResource("/shaders/fragment.frag"))
        sceneShaderProgram.link()


        sceneShaderProgram.createUniform("projection")
        sceneShaderProgram.createUniform("view" )
        sceneShaderProgram.createUniform("model")
      //  sceneShaderProgram.createUniform("useTexture")
        sceneShaderProgram.createUniform("shininess")

        sceneShaderProgram.createUniform("viewPos")

        sceneShaderProgram.createUniform("dirLight.direction")
        sceneShaderProgram.createUniform("dirLight.ambient")
        sceneShaderProgram.createUniform("dirLight.diffuse")
        sceneShaderProgram.createUniform("dirLight.specular")

        sceneShaderProgram.createUniform("pointLight.position")
        sceneShaderProgram.createUniform("pointLight.constant")
        sceneShaderProgram.createUniform("pointLight.linear")
        sceneShaderProgram.createUniform("pointLight.quadratic")
        sceneShaderProgram.createUniform("pointLight.ambient")
        sceneShaderProgram.createUniform("pointLight.diffuse")
        sceneShaderProgram.createUniform("pointLight.specular")
       // sceneShaderProgram.createUniform("lightPos")
       // sceneShaderProgram.createUniform("lightColor")

    }

    private def setupGuiShader() {
        guiShaderProgram = new ShaderProgram
        guiShaderProgram.createVertexShader(Utils.loadResource("/shaders/hud_vertex.vs"))
        guiShaderProgram.createFragmentShader(Utils.loadResource("/shaders/hud_fragment.frag"))
        guiShaderProgram.link()

        guiShaderProgram.createUniform("projectionMatrix")
        guiShaderProgram.createUniform("modelMatrix")
        guiShaderProgram.createUniform("colour")
       // guiShaderProgram.createUniform("useTexture")
    }

    def render(camera: Camera) {

        val projectionMatrix: Matrix4f = transformation.updateProjectionMatrix(FOV, 800, 600, Z_NEAR, Z_FAR)
        val viewMatrix: Matrix4f = transformation.updateViewMatrix(camera)

        projectionMatrix.get(_proj.clear().asInstanceOf[FloatBuffer])
        //projectionMatrix.mul(viewMatrix)
        viewMatrix.get(_modl.clear().asInstanceOf[FloatBuffer])

        frustum = Frustum.getFrustum(_proj, _modl)

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT)
        if (tar.world != null) renderScene()
        renderGui()
    }

    private def renderScene() {

        Renderer.bindShaderProgram(sceneShaderProgram)

        sceneShaderProgram.setUniform("projection", transformation.projectionMatrix)
        sceneShaderProgram.setUniform("view", transformation.viewMatrix)

        sceneShaderProgram.setUniform("viewPos",tar.camera.position )


        sceneShaderProgram.setUniform("dirLight.direction",new Vec3f(-0.2,-1,-0.3))
        sceneShaderProgram.setUniform("dirLight.ambient",new Vec3f(0.3,0.3,0.3))
        sceneShaderProgram.setUniform("dirLight.diffuse",new Vec3f(0.5,0.5,0.5))
        sceneShaderProgram.setUniform("dirLight.specular",new Vec3f(0.5,0.5,0.5))
        sceneShaderProgram.setUniform("shininess",32)



        sceneShaderProgram.setUniform("pointLight.position",new Vec3f(0,10,0))
        sceneShaderProgram.setUniform("pointLight.constant", 1.0f)
        sceneShaderProgram.setUniform("pointLight.linear",0.09f)
        sceneShaderProgram.setUniform("pointLight.quadratic",0.032f)
        sceneShaderProgram.setUniform("pointLight.ambient",new Vec3f(0.05,0.05,0.05))
        sceneShaderProgram.setUniform("pointLight.diffuse",new Vec3f(0.8,0.8,0.8))
        sceneShaderProgram.setUniform("pointLight.specular",new Vec3f(1,1,1))





       // sceneShaderProgram.setUniform("lightPos", new Vec3f(100,100,100))
       // sceneShaderProgram.setUniform("lightColor", new Vec3f(1,1,1))

        glEnable(GL_CULL_FACE)

        RenderChunk.clearRend()


        sceneShaderProgram.setUniform("model",  transformation.buildChunkModelViewMatrix(new ChunkPosition(0,0,0)))
        mesh.render



        sceneShaderProgram.setUniform("model",  transformation.buildChunkModelViewMatrix(new ChunkPosition(0,0,1)))
        mesh.render
        worldRenderer.getRenderChunks(null/*tar.player*/).foreach((renderChunk: RenderChunk) => {
            //if(frustum.chunkInFrustum(renderChunk.chunkPosition)){
           // sceneShaderProgram.setUniform("modelViewMatrix",  transformation.buildChunkModelViewMatrix(new ChunkPosition(0,0,0)))
                sceneShaderProgram.setUniform("model", transformation.buildChunkModelViewMatrix(renderChunk.chunk.position))
                renderChunk.render(0)
           // }
        })


        glDisable(GL_CULL_FACE)

        val objectMouseOver = tar.objectMouseOver
        if (objectMouseOver != null) {
            sceneShaderProgram.setUniform("model", transformation.buildObjectMouseOverViewMatrix(objectMouseOver.blockPos))
            worldRenderer.renderBlockMouseOver()
        }




        Renderer.unbindShaderProgram()
    }

    private def renderGui() {
        glEnable(GL_BLEND)
        glEnable(GL_CULL_FACE)
        glDisable(GL_DEPTH_TEST)

        Renderer.bindShaderProgram(guiShaderProgram)


        val ortho: Matrix4f = transformation.getOrtho2DProjectionMatrix(0, 800, 0, 600)
        guiShaderProgram.setUniform("projectionMatrix", ortho)
        tar.guiManager.draw(Mouse.getX, Mouse.getY)


        Renderer.unbindShaderProgram()


        glEnable(GL_DEPTH_TEST)
        glDisable(GL_BLEND)
        glDisable(GL_CULL_FACE)
    }

}

object Renderer {
    var currentShaderProgram: ShaderProgram = _

    def bindShaderProgram(shaderProgram: ShaderProgram): Unit = {
        if (currentShaderProgram ne null) currentShaderProgram.unbind()
        currentShaderProgram = shaderProgram
        currentShaderProgram.bind()
    }

    def unbindShaderProgram(): Unit = {
        if (currentShaderProgram ne null) currentShaderProgram.unbind()
        currentShaderProgram = null
    }
}
