package ru.megains.tartess.client.renderer.shader


import ru.megains.tartess.client.renderer.Renderer
import ru.megains.tartess.client.renderer.light.{DirLight, PointLight, SpotLight}

class LightShaderProgram extends SceneShaderProgram {


    override val dir: String = "light"

    override def createUniforms(): Unit = {
        super.createUniforms()

        createUniform("viewPos")
        createUniform("pointLightSize")
        createUniform("spotLightSize")

        DirLight.createUniforms(this)
        PointLight.createUniforms(this)
        SpotLight.createUniforms(this)

    }

    override def setUniforms(renderer: Renderer): Unit = {
        super.setUniforms(renderer)

        setUniform("viewPos",renderer.tar.camera.position )
        setUniform("pointLightSize",0)
        setUniform("spotLightSize",0)

        renderer.dirLight.setUniforms(this)
    }
}
