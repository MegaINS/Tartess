package ru.megains.tartess.renderer.shader


import ru.megains.tartess.renderer.Renderer
import ru.megains.tartess.renderer.light.{DirLight, PointLight, SpotLight}

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

        //            sceneShaderProgram.setUniform("pointLight.position",new Vec3f(0,3,0))
        //            sceneShaderProgram.setUniform("pointLight.constant", 1.0f)
        //            sceneShaderProgram.setUniform("pointLight.linear",0.09f)
        //            sceneShaderProgram.setUniform("pointLight.quadratic",0.032f)
        //            sceneShaderProgram.setUniform("pointLight.ambient",new Vec3f(0.2,0.25,0.4))
        //            sceneShaderProgram.setUniform("pointLight.diffuse",new Vec3f(0.4,0.5,0.8))
        //            sceneShaderProgram.setUniform("pointLight.specular",new Vec3f(0.4,0.5,0.8))




        //            sceneShaderProgram.setUniform("spotLight.position",new Vec3f(-0.5,10,-0.5))
        //            sceneShaderProgram.setUniform("spotLight.direction", new Vec3f(0.05,-1,0.05))
        //            sceneShaderProgram.setUniform("spotLight.ambient",new Vec3f(0.5,0.25,0))
        //            sceneShaderProgram.setUniform("spotLight.diffuse",new Vec3f(1,0.5,0))
        //            sceneShaderProgram.setUniform("spotLight.specular",new Vec3f(1,1,1))
        //            sceneShaderProgram.setUniform("spotLight.linear",0.09f)
        //            sceneShaderProgram.setUniform("spotLight.quadratic",0.032f)
        //            sceneShaderProgram.setUniform("spotLight.constant",1f)
        //            sceneShaderProgram.setUniform("spotLight.cutOff",Math.cos(Math.toRadians(12.5f)) toFloat)
        //            sceneShaderProgram.setUniform("spotLight.outerCutOff",Math.cos(Math.toRadians(15.0f)) toFloat)
    }
}
