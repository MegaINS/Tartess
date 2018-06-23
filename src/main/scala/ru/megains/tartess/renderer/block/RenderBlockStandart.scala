package ru.megains.tartess.renderer.block

import ru.megains.tartess.block.data.{BlockDirection, BlockState}
import ru.megains.tartess.physics.AABB
import ru.megains.tartess.renderer.api.RenderBlock
import ru.megains.tartess.world.World
import ru.megains.tartess.world.chunk.ChunkPosition


object RenderBlockStandart extends RenderBlock {


    override def render(blockState: BlockState, world: World, chunkPosition:ChunkPosition): Boolean = {

        val pos = blockState.pos
        val block = blockState.block
        val aabb:AABB = blockState.getSelectedBoundingBox.div(16).sum(pos.rendX, pos.rendY, pos.rendZ)
        val minX = aabb.minX
        val minY = aabb.minY
        val minZ = aabb.minZ
        val maxX = aabb.maxX
        val maxY = aabb.maxY
        val maxZ = aabb.maxZ
        var isRender = false
     //   val blockBody = blockState.getBoundingBox



       // if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.SOUTH).sum(BlockDirection.SOUTH))) {
            RenderBlock.renderSideSouth(minX, maxX, minY, maxY, maxZ, block.getATexture(pos,BlockDirection.SOUTH,world))
            isRender = true
       // }

       // if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.NORTH).sum(BlockDirection.NORTH))) {
            RenderBlock.renderSideNorth(minX, maxX, minY, maxY, minZ, block.getATexture(pos,BlockDirection.NORTH,world))
            isRender = true
       // }

        // if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.DOWN).sum(BlockDirection.DOWN))) {
            RenderBlock.renderSideDown(minX, maxX, minY, minZ, maxZ, block.getATexture(pos,BlockDirection.DOWN,world))
            isRender = true
       // }


      //  if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.UP).sum(BlockDirection.UP))) {
            RenderBlock.renderSideUp(minX, maxX, maxY, minZ, maxZ, block.getATexture(pos,BlockDirection.UP,world))
            isRender = true
       // }

       // if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.WEST).sum(BlockDirection.WEST))) {
            RenderBlock.renderSideWest(minX, minY, maxY, minZ, maxZ, block.getATexture(pos,BlockDirection.WEST,world))
            isRender = true
      //  }

        //if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.EAST).sum(BlockDirection.EAST))) {
            RenderBlock.renderSideEast(maxX, minY, maxY, minZ, maxZ, block.getATexture(pos,BlockDirection.EAST,world))
            isRender = true
        //}
        isRender
    }
}
