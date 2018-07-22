package ru.megains.tartess.renderer.block

import ru.megains.tartess.block.data.{BlockDirection, BlockState}
import ru.megains.tartess.renderer.api.TRenderBlock
import ru.megains.tartess.world.World
import ru.megains.tartess.world.chunk.data.ChunkPosition


object RenderBlockStandart extends TRenderBlock {


    override def render(blockState: BlockState, world: World, chunkPosition:ChunkPosition): Boolean = {

        var isRender = false

        val pos = blockState.pos
        val blockBodies  = blockState.getBlockBody.div(16).sum(pos.rendX, pos.rendY, pos.rendZ)

        for(aabb<-blockBodies.hashSet){

            val minX = aabb.minX
            val minY = aabb.minY
            val minZ = aabb.minZ
            val maxX = aabb.maxX
            val maxY = aabb.maxY
            val maxZ = aabb.maxZ


            RenderBlock.renderSideSouth(minX, maxX, minY, maxY, maxZ, blockState.getTTexture(BlockDirection.SOUTH,world))
            isRender = true

            RenderBlock.renderSideNorth(minX, maxX, minY, maxY, minZ, blockState.getTTexture(BlockDirection.NORTH,world))
            isRender = true

            RenderBlock.renderSideDown(minX, maxX, minY, minZ, maxZ, blockState.getTTexture(BlockDirection.DOWN,world))
            isRender = true

            RenderBlock.renderSideUp(minX, maxX, maxY, minZ, maxZ, blockState.getTTexture(BlockDirection.UP,world))
            isRender = true

            RenderBlock.renderSideWest(minX, minY, maxY, minZ, maxZ, blockState.getTTexture(BlockDirection.WEST,world))
            isRender = true

            RenderBlock.renderSideEast(maxX, minY, maxY, minZ, maxZ, blockState.getTTexture(BlockDirection.EAST,world))
            isRender = true

        }


//        val aabb:AABB = blockState.getBlockBody(0).div(16).sum(pos.rendX, pos.rendY, pos.rendZ)
//        val minX = aabb.minX
//        val minY = aabb.minY
//        val minZ = aabb.minZ
//        val maxX = aabb.maxX
//        val maxY = aabb.maxY
//        val maxZ = aabb.maxZ
//
//       // val blockBody = blockState.getBoundingBox
//
//
//
//       // if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.SOUTH).sum(BlockDirection.SOUTH))) {
//            RenderBlock.renderSideSouth(minX, maxX, minY, maxY, maxZ, blockState.getTTexture(BlockDirection.SOUTH,world))
//            isRender = true
//       // }
//
//       // if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.NORTH).sum(BlockDirection.NORTH))) {
//            RenderBlock.renderSideNorth(minX, maxX, minY, maxY, minZ, blockState.getTTexture(BlockDirection.NORTH,world))
//            isRender = true
//       // }
//
//        // if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.DOWN).sum(BlockDirection.DOWN))) {
//            RenderBlock.renderSideDown(minX, maxX, minY, minZ, maxZ, blockState.getTTexture(BlockDirection.DOWN,world))
//            isRender = true
//       // }
//
//
//       // if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.UP))) {
//            RenderBlock.renderSideUp(minX, maxX, maxY, minZ, maxZ, blockState.getTTexture(BlockDirection.UP,world))
//            isRender = true
//      //  }
//
//       // if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.WEST).sum(BlockDirection.WEST))) {
//            RenderBlock.renderSideWest(minX, minY, maxY, minZ, maxZ, blockState.getTTexture(BlockDirection.WEST,world))
//            isRender = true
//      //  }
//
//      //  if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.EAST).sum(BlockDirection.EAST))) {
//            RenderBlock.renderSideEast(maxX, minY, maxY, minZ, maxZ, blockState.getTTexture(BlockDirection.EAST,world))
//            isRender = true
//       // }





        isRender
    }
}
