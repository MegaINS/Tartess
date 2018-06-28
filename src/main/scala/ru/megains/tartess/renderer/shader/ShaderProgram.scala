package ru.megains.tartess.renderer.shader

import org.joml.Matrix4f
import org.lwjgl.opengl.GL20._
import ru.megains.tartess.utils.Vec3f

import scala.collection.mutable

class ShaderProgram {

    val programId: Int = glCreateProgram
    if (programId == 0) throw new Exception("Could not create Shader")

    var vertexShaderId:Int = 0
    var fragmentShaderId:Int  = 0

    var uniforms:mutable.HashMap[String, UniformData] = new mutable.HashMap()


    def createShader(shaderCode: String, shaderType: Int): Int = {
        val shaderId = glCreateShader(shaderType)
        if (shaderId == 0) throw new Exception("Error creating shader. Code: " + shaderId)
        glShaderSource(shaderId, shaderCode)
        glCompileShader(shaderId)
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024))
        glAttachShader(programId, shaderId)
        shaderId
    }

    def createVertexShader(shaderCode: String): Unit = {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER)
    }

    def createFragmentShader(shaderCode: String): Unit = {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER)
    }

    def link(): Unit = {
        glLinkProgram(programId)
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) throw new Exception("Error linking Shader code: " + glGetShaderInfoLog(programId, 1024))
        glValidateProgram(programId)
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) println("Warning validating Shader code: " + glGetShaderInfoLog(programId, 1024))
    }


    def createUniform(uniformName: String): Unit = {
        val uniformLocation = glGetUniformLocation(programId, uniformName)
        if (uniformLocation < 0) throw new Exception("Could not find uniform:" + uniformName)
        uniforms.put(uniformName, new UniformData(uniformLocation))
    }

    def setUniform(uniformName: String, value: Matrix4f): Unit = {
        val uniformData = getUniform(uniformName)
        value.get(uniformData.floatBuffer)
        glUniformMatrix4fv(uniformData.uniformLocation, false, uniformData.floatBuffer)
    }

    def setUniform(uniformName: String, value: Boolean): Unit = {
        val uniformData = getUniform(uniformName)
        glUniform1i(uniformData.uniformLocation, if (value) 1 else 0)
    }

    def setUniform(uniformName: String, value: Float): Unit = {
        val uniformData = getUniform(uniformName)
        glUniform1f(uniformData.uniformLocation, value)
    }

    def setUniform(uniformName: String, value: Vec3f): Unit = {
        val uniformData = getUniform(uniformName)
        glUniform3f(uniformData.uniformLocation, value.x, value.y, value.z)
    }


    def getUniform(uniformName: String): UniformData ={
        val uniformData = uniforms(uniformName)
        if (uniformData == null) throw new RuntimeException("Uniform [" + uniformName + "] has nor been created")
        uniformData
    }

    def bind(): Unit = glUseProgram(programId)

    def unbind(): Unit = glUseProgram(0)


}
