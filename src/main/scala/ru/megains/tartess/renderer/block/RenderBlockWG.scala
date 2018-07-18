package ru.megains.tartess.renderer.block

import ru.megains.tartess.block.data.{BlockDirection, BlockState}
import ru.megains.tartess.physics.AABB
import ru.megains.tartess.renderer.api.TRenderBlock
import ru.megains.tartess.world.World
import ru.megains.tartess.world.chunk.data.ChunkPosition


object RenderBlockWG extends TRenderBlock {


    override def render(blockState: BlockState, world: World, chunkPosition:ChunkPosition): Boolean = {

        var isRender = false

        val pos = blockState.pos
        val block = blockState.block
        val aabb:AABB = blockState.getBlockBody(0).div(16).sum(pos.rendX, pos.rendY, pos.rendZ)
        val minX = aabb.minX
        val minY = aabb.minY
        val minZ = aabb.minZ
        val maxX = aabb.maxX
        val maxY = aabb.maxY
        val maxZ = aabb.maxZ


        if (!world.isOpaqueCube(pos,BlockDirection.SOUTH)) {
            RenderBlock.renderSideSouth(minX, maxX, minY, maxY, maxZ, block.getATexture(pos,BlockDirection.SOUTH,world))
            isRender = true
        }

        if (!world.isOpaqueCube(pos,BlockDirection.NORTH)) {
            RenderBlock.renderSideNorth(minX, maxX, minY, maxY, minZ, block.getATexture(pos,BlockDirection.NORTH,world))
            isRender = true
        }

        if (!world.isOpaqueCube(pos,BlockDirection.DOWN)) {
            RenderBlock.renderSideDown(minX, maxX, minY, minZ, maxZ, block.getATexture(pos,BlockDirection.DOWN,world))
            isRender = true
        }


        if (!world.isOpaqueCube(pos,BlockDirection.UP)) {
            RenderBlock.renderSideUp(minX, maxX, maxY, minZ, maxZ, block.getATexture(pos,BlockDirection.UP,world))
            isRender = true
        }

        if (!world.isOpaqueCube(pos,BlockDirection.WEST)) {
            RenderBlock.renderSideWest(minX, minY, maxY, minZ, maxZ, block.getATexture(pos,BlockDirection.WEST,world))
            isRender = true
        }

        if (!world.isOpaqueCube(pos,BlockDirection.EAST)) {
            RenderBlock.renderSideEast(maxX, minY, maxY, minZ, maxZ, block.getATexture(pos,BlockDirection.EAST,world))
            isRender = true
        }
        isRender
    }
}

