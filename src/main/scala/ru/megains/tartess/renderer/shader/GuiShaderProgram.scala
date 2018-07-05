package ru.megains.tartess.renderer.shader
import org.joml.Matrix4f
import ru.megains.tartess.Tartess
import ru.megains.tartess.renderer.Renderer

class GuiShaderProgram extends ShaderProgram {

    override val dir: String = "gui"

    override def createUniforms(): Unit = {
        createUniform("projectionMatrix")
        createUniform("modelMatrix")
        createUniform("colour")
        createUniform("useTexture")
    }

    override def setUniforms(renderer: Renderer): Unit = {
        val ortho: Matrix4f = renderer.transformation.getOrtho2DProjectionMatrix(0, Tartess.tartess.window.width, 0, Tartess.tartess.window.height)
        setUniform("projectionMatrix", ortho)
    }
}
