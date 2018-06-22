package ru.megains.old.graph.renderer.gui

import org.joml.{Matrix4f, Vector3f}
import ru.megains.old.graph.Renderer
import ru.megains.old.graph.renderer.mesh.Mesh


class GuiInventory extends GuiScreen{
  //  private[graph] var selectItemPos: GuiRenderInfo = new GuiRenderInfo(240, 40)
    private[graph] var rectPos: GuiRenderInfo = new GuiRenderInfo(200,0,0,0,0,1f)
    var rect:Mesh = null
    var rock:String = "rock"

    override def init(): Unit = {

        rect = createTextureRect(400,80,rock)
    }



    override def render(renderer: Renderer): Unit = {
      //  val item: Item = Item.getItemById(CubeGame.megaGame.blockSelect)
        var projModelMatrix: Matrix4f = null


        projModelMatrix = renderer.transformation.buildOrtoProjModelMatrix(rectPos)
      Renderer.currentShaderProgram.setUniform("modelMatrix", projModelMatrix)
      Renderer.currentShaderProgram.setUniform("colour", new Vector3f(1f, 1f, 1f))
     //   shaderProgram.setUniform("useTexture", 0)
        rect.render(renderer.textureManager)


       // projModelMatrix = renderer.transformation.buildOrtoProjModelMatrix(selectItemPos)
      Renderer.currentShaderProgram.setUniform("modelMatrix", projModelMatrix)
      Renderer.currentShaderProgram.setUniform("colour", new Vector3f(1f, 1f, 1f))
       // GameRegister.getItemRender(item).renderInInventory(shaderProgram,renderer.textureManager )

       // CubeGame.megaGame.textureManager.bindTexture(rock)



    }

    override def cleanup(): Unit = {
        rect.cleanUp()
    }
}
