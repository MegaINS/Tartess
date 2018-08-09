package ru.megains.tartess.client.renderer.api

import ru.megains.tartess.client.renderer.texture.TTextureRegister

trait TTexture {

    def registerTexture(textureRegister: TTextureRegister): Unit

}
