package ru.megains.tech.world.chunk

import ru.megains.tech.block.blockdata.BlockPos
import ru.megains.tech.world.World
import ru.megains.tech.world.chunk.data.ChunkPosition


class ChunkVoid( position: ChunkPosition,world: World) extends Chunk(position,world) {


    override def isAirBlock(pos: BlockPos): Boolean = true


}
