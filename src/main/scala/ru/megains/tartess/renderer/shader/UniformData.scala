package ru.megains.tartess.renderer.shader

import java.nio.FloatBuffer

import org.lwjgl.BufferUtils

class UniformData(val uniformLocation: Int) {

     val floatBuffer: FloatBuffer = BufferUtils.createFloatBuffer(16)

}
