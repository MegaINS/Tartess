package ru.megains.tartess.renderer.light

import ru.megains.tartess.renderer.shader.ShaderProgram
import ru.megains.tartess.utils.Vec3f

class SpotLight {

    val position:Vec3f  = new Vec3f()
    val direction:Vec3f  = new Vec3f()
    val ambient:Vec3f  = new Vec3f()
    val diffuse:Vec3f  = new Vec3f()
    val specular:Vec3f  = new Vec3f()
    var constant: Float = 0f
    var linear: Float = 0f
    var quadratic: Float = 0f
    var shininess: Float = 0f
    var cutOff: Float = 0f
    var outerCutOff: Float = 0f
    
    
    def setUniforms(shaderProgram: ShaderProgram): Unit = {

        shaderProgram.setUniform("spotLight.position",position)
        shaderProgram.setUniform("spotLight.direction",direction)
        shaderProgram.setUniform("spotLight.ambient",ambient)
        shaderProgram.setUniform("spotLight.diffuse",diffuse)
        shaderProgram.setUniform("spotLight.specular",specular)
        shaderProgram.setUniform("spotLight.constant",constant)
        shaderProgram.setUniform("spotLight.linear",linear)
        shaderProgram.setUniform("spotLight.quadratic",quadratic)
        shaderProgram.setUniform("spotLight.shininess",shininess)
        shaderProgram.setUniform("spotLight.cutOff",cutOff)
        shaderProgram.setUniform("spotLight.outerCutOff",outerCutOff)
    }
}

object SpotLight{

    def createUniforms(shaderProgram: ShaderProgram): Unit = {

        shaderProgram.createUniform("spotLight.position")
        shaderProgram.createUniform("spotLight.direction")
        shaderProgram.createUniform("spotLight.ambient")
        shaderProgram.createUniform("spotLight.diffuse")
        shaderProgram.createUniform("spotLight.specular")
        shaderProgram.createUniform("spotLight.constant")
        shaderProgram.createUniform("spotLight.linear")
        shaderProgram.createUniform("spotLight.quadratic")
        shaderProgram.createUniform("spotLight.shininess")
        shaderProgram.createUniform("spotLight.cutOff")
        shaderProgram.createUniform("spotLight.outerCutOff")
    }
}
