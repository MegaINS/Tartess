package ru.megains.tartess.periphery

import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.{GL, GL11}
import org.lwjgl.system.MemoryUtil.NULL

class Window {


    var width: Int =800
    var height: Int = 600
    var id: Long = NULL

    def create(): Unit = {

        if (!glfwInit) {
            throw new RuntimeException("Failed to init GLFW.")
        }
        glfwWindowHint(GLFW_DEPTH_BITS, 24)
        id = glfwCreateWindow(width, height, "Tartess", NULL, NULL)
        if (id == NULL) {
            glfwTerminate()
            throw new RuntimeException
        }

        glfwSetWindowSizeCallback(id,(window: Long, width: Int, height: Int)=>{
           this.width = width
            this.height = height
           GL11.glViewport(0, 0, width, height)
        })
        glfwMakeContextCurrent(id)
        GL.createCapabilities
        glfwSwapInterval(0)
    }

    def isClose: Boolean = glfwWindowShouldClose(id)

    def update(): Unit = {

        glfwSwapBuffers(id)
    }

    def destroy(): Unit = {
        if (id != NULL) {
            glfwDestroyWindow(id)
        }
    }

}
