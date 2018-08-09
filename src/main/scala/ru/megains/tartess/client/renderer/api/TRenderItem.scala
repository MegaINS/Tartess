package ru.megains.tartess.client.renderer.api

trait  TRenderItem  extends TTexture{

    def renderInInventory(): Unit

    def renderInWorld(): Unit

}
