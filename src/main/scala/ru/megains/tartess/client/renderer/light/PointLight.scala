package ru.megains.tartess.client.renderer.light

import ru.megains.tartess.client.renderer.shader.ShaderProgram
import ru.megains.tartess.common.utils.Vec3f

class PointLight {

    val position:Vec3f  = new Vec3f()
    val ambient:Vec3f  = new Vec3f()
    val diffuse:Vec3f  = new Vec3f()
    val specular:Vec3f  = new Vec3f()
    var constant: Float = 0f
    var linear: Float = 0f
    var quadratic: Float = 0f
    var shininess: Float = 0f



    def setUniforms(shaderProgram: ShaderProgram): Unit = {
        shaderProgram.setUniform("pointLight.position",position)
        shaderProgram.setUniform("pointLight.ambient",ambient)
        shaderProgram.setUniform("pointLight.diffuse",diffuse)
        shaderProgram.setUniform("pointLight.specular",specular)
        shaderProgram.setUniform("pointLight.constant",constant)
        shaderProgram.setUniform("pointLight.linear",linear)
        shaderProgram.setUniform("pointLight.quadratic",quadratic)
        shaderProgram.setUniform("pointLight.shininess",shininess)
    }
}

object PointLight{

    def createUniforms(shaderProgram: ShaderProgram): Unit = {
        shaderProgram.createUniform("pointLight.position")
        shaderProgram.createUniform("pointLight.ambient")
        shaderProgram.createUniform("pointLight.diffuse")
        shaderProgram.createUniform("pointLight.specular")
        shaderProgram.createUniform("pointLight.constant")
        shaderProgram.createUniform("pointLight.linear")
        shaderProgram.createUniform("pointLight.quadratic")
        shaderProgram.createUniform("pointLight.shininess")
    }
}
