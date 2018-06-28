package ru.megains.tartess.renderer.mesh

import org.lwjgl.opengl.GL30._

class  MeshTextureNormals(makeMode: Int, textureName: String, indices: Array[Int], positions: Array[Float], colours: Array[Float], textCoords: Array[Float], normals: Array[Float],useTexture:Boolean)
        extends MeshTexture(makeMode, textureName, indices, positions, colours, textCoords,useTexture) {

    glBindVertexArray(vaoId)
    bindArray(3, 3, normals)
    glBindVertexArray(0)

}
