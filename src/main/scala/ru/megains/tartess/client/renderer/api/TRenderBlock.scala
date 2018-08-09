package ru.megains.tartess.client.renderer.api

import ru.megains.tartess.client.renderer.texture.TextureAtlas
import ru.megains.tartess.common.block.data.{BlockDirection, BlockState}
import ru.megains.tartess.common.world.World
import ru.megains.tartess.common.world.chunk.data.ChunkPosition

trait TRenderBlock extends TTexture{

    def render(blockState: BlockState, world: World, posWorld: ChunkPosition): Boolean

    def getATexture(blockState: BlockState = null,blockDirection: BlockDirection = BlockDirection.UP,world: World = null): TextureAtlas
}
