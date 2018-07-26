package ru.megains.tartess.client.renderer.api

import ru.megains.tartess.client.renderer.texture.{TTextureRegister, TextureAtlas}
import ru.megains.tartess.common.block.data.{BlockDirection, BlockState}
import ru.megains.tartess.common.world.World
import ru.megains.tartess.common.world.chunk.data.ChunkPosition

trait TRenderBlock {

    def render(blockState: BlockState, world: World, posWorld: ChunkPosition): Boolean

    def registerTexture(textureRegister: TTextureRegister): Unit

    def getATexture(blockState: BlockState = null,blockDirection: BlockDirection = BlockDirection.UP,world: World = null): TextureAtlas
}
