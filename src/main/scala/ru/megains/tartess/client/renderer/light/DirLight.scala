package ru.megains.tartess.client.renderer.light

import ru.megains.tartess.client.renderer.shader.ShaderProgram
import ru.megains.tartess.common.utils.Vec3f

class DirLight {
    val direction:Vec3f  = new Vec3f()
    val ambient:Vec3f  = new Vec3f()
    val diffuse:Vec3f  = new Vec3f()
    val specular:Vec3f  = new Vec3f()
    var shininess: Float = 0f

    def setUniforms(shaderProgram: ShaderProgram): Unit = {
        shaderProgram.setUniform("dirLight.direction",direction)
        shaderProgram.setUniform("dirLight.ambient",ambient)
        shaderProgram.setUniform("dirLight.diffuse",diffuse)
        shaderProgram.setUniform("dirLight.specular",specular)
        shaderProgram.setUniform("dirLight.shininess",shininess)
    }
}

object DirLight{

    def createUniforms(shaderProgram: ShaderProgram): Unit = {
        shaderProgram.createUniform("dirLight.direction")
        shaderProgram.createUniform("dirLight.ambient")
        shaderProgram.createUniform("dirLight.diffuse")
        shaderProgram.createUniform("dirLight.specular")
        shaderProgram.createUniform("dirLight.shininess")
    }
}
