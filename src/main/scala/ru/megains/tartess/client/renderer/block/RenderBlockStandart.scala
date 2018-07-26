package ru.megains.tartess.client.renderer.block

import ru.megains.tartess.client.renderer.api.TRenderBlock
import ru.megains.tartess.client.renderer.texture.{TTextureRegister, TextureAtlas}
import ru.megains.tartess.common.block.Block
import ru.megains.tartess.common.block.data.{BlockDirection, BlockState}
import ru.megains.tartess.common.world.World
import ru.megains.tartess.common.world.chunk.data.ChunkPosition


class RenderBlockStandart(block:Block) extends TRenderBlock {

    var texture: TextureAtlas = _

    override def registerTexture(textureRegister: TTextureRegister): Unit = {
        texture = textureRegister.registerTexture(block.name)
    }


    override def getATexture(blockState: BlockState,blockDirection: BlockDirection,world: World ): TextureAtlas = texture


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


            RenderBlock.renderSideSouth(minX, maxX, minY, maxY, maxZ, texture)
            isRender = true

            RenderBlock.renderSideNorth(minX, maxX, minY, maxY, minZ,texture)
            isRender = true

            RenderBlock.renderSideDown(minX, maxX, minY, minZ, maxZ,texture)
            isRender = true

            RenderBlock.renderSideUp(minX, maxX, maxY, minZ, maxZ, texture)
            isRender = true

            RenderBlock.renderSideWest(minX, minY, maxY, minZ, maxZ, texture)
            isRender = true

            RenderBlock.renderSideEast(maxX, minY, maxY, minZ, maxZ, texture)
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
//            RenderBlock.renderSideSouth(minX, maxX, minY, maxY, maxZ, getATexture(blockState,BlockDirection.SOUTH,world))
//            isRender = true
//       // }
//
//       // if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.NORTH).sum(BlockDirection.NORTH))) {
//            RenderBlock.renderSideNorth(minX, maxX, minY, maxY, minZ, getATexture(blockState,BlockDirection.NORTH,world))
//            isRender = true
//       // }
//
//        // if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.DOWN).sum(BlockDirection.DOWN))) {
//            RenderBlock.renderSideDown(minX, maxX, minY, minZ, maxZ, getATexture(blockState,BlockDirection.DOWN,world))
//            isRender = true
//       // }
//
//
//       // if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.UP))) {
//            RenderBlock.renderSideUp(minX, maxX, maxY, minZ, maxZ, getATexture(blockState,BlockDirection.UP,world))
//            isRender = true
//      //  }
//
//       // if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.WEST).sum(BlockDirection.WEST))) {
//            RenderBlock.renderSideWest(minX, minY, maxY, minZ, maxZ, getATexture(blockState,BlockDirection.WEST,world))
//            isRender = true
//      //  }
//
//      //  if (!world.isOpaqueCube(blockBody.getSidePos(BlockDirection.EAST).sum(BlockDirection.EAST))) {
//            RenderBlock.renderSideEast(maxX, minY, maxY, minZ, maxZ, getATexture(blockState,BlockDirection.EAST,world))
//            isRender = true
//       // }





        isRender
    }
}

object RenderBlockStandart{
    def apply(block: Block): RenderBlockStandart = new RenderBlockStandart(block)
}
