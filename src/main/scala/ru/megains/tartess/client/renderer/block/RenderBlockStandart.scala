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

        isRender
    }
}

object RenderBlockStandart{
    def apply(block: Block): RenderBlockStandart = new RenderBlockStandart(block)
}
