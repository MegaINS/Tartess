package ru.megains.tartess.client.renderer.shader

import ru.megains.tartess.client.renderer.Renderer

class SceneShaderProgram extends ShaderProgram {


    override val dir: String = "basic"

    override def createUniforms(): Unit = {
        createUniform("projection")
        createUniform("view" )
        createUniform("model")
        createUniform("useTexture")
    }

    override def setUniforms(renderer: Renderer): Unit = {
        setUniform("projection", renderer.transformation.projectionMatrix)
        setUniform("view", renderer.transformation.viewMatrix)
    }

}
