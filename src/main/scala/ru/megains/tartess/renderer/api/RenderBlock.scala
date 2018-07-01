package ru.megains.tartess.renderer.api

import ru.megains.tartess.block.data.BlockState
import ru.megains.tartess.world.World
import ru.megains.tartess.world.chunk.data.ChunkPosition

abstract class RenderBlock {

    def render(blockState: BlockState, world: World, posWorld: ChunkPosition): Boolean
}
