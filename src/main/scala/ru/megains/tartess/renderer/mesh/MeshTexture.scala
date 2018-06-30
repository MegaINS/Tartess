package ru.megains.tartess.renderer.mesh

import org.lwjgl.opengl.GL13._
import org.lwjgl.opengl.GL30._
import ru.megains.tartess.renderer.Renderer
import ru.megains.tartess.renderer.texture.TextureManager


class MeshTexture( makeMode: Int ,val textureName:String, indices: Array[Int], positions: Array[Float], colours: Array[Float],textCoords: Array[Float],useTexture:Boolean)
        extends Mesh( makeMode,indices, positions, colours){
    glBindVertexArray(vaoId)
    bindArray(2,2,textCoords)
    glBindVertexArray(0)


    override def initRender(textureManager:TextureManager): Unit ={
        super.initRender(textureManager)
        Renderer.currentShaderProgram.setUniform("useTexture", useTexture)
        glActiveTexture(GL_TEXTURE0)
        textureManager.bindTexture(textureName)

    }
    override def endRender(): Unit ={
        super.endRender()
    }
}
