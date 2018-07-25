package ru.megains.tartess.client.renderer.api

import ru.megains.tartess.common.block.data.BlockState
import ru.megains.tartess.common.world.World
import ru.megains.tartess.common.world.chunk.data.ChunkPosition

trait TRenderBlock {

    def render(blockState: BlockState, world: World, posWorld: ChunkPosition): Boolean
}
