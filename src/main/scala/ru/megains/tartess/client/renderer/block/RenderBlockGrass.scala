package ru.megains.tartess.client.renderer.block

import ru.megains.tartess.client.renderer.api.TRenderBlock
import ru.megains.tartess.client.renderer.texture.{TTextureRegister, TextureAtlas}
import ru.megains.tartess.common.block.Block
import ru.megains.tartess.common.block.data.{BlockDirection, BlockState}
import ru.megains.tartess.common.physics.AABB
import ru.megains.tartess.common.world.World
import ru.megains.tartess.common.world.chunk.data.ChunkPosition

class RenderBlockGrass(block:Block) extends TRenderBlock{

    val name = block.name
    var texture: TextureAtlas = _
    var aTextureUp: TextureAtlas = _
    var aTextureDown: TextureAtlas = _


    override def registerTexture(textureRegister: TTextureRegister): Unit = {

        texture = textureRegister.registerTexture(name + "_side")
        aTextureUp = textureRegister.registerTexture(name + "_up")
        aTextureDown = textureRegister.registerTexture(name + "_down")
    }

    override def getATexture(blockState: BlockState,blockDirection: BlockDirection,world: World): TextureAtlas = {
        blockDirection match {
            case BlockDirection.UP => aTextureUp
            case BlockDirection.DOWN => aTextureDown
            case _ => texture
        }

    }

    override def render(blockState: BlockState, world: World, posWorld: ChunkPosition): Boolean = {

        var isRender = false

        val pos = blockState.pos
        val block = blockState.block
        val aabb:AABB = blockState.getBlockBody.hashSet.last.div(16).sum(pos.rendX, pos.rendY, pos.rendZ)
        val minX = aabb.minX
        val minY = aabb.minY
        val minZ = aabb.minZ
        val maxX = aabb.maxX
        val maxY = aabb.maxY
        val maxZ = aabb.maxZ


        if (!world.isOpaqueCube(pos,BlockDirection.SOUTH)) {
            RenderBlock.renderSideSouth(minX, maxX, minY, maxY, maxZ, getATexture(blockState,BlockDirection.SOUTH,world))
            isRender = true
        }

        if (!world.isOpaqueCube(pos,BlockDirection.NORTH)) {
            RenderBlock.renderSideNorth(minX, maxX, minY, maxY, minZ, getATexture(blockState,BlockDirection.NORTH,world))
            isRender = true
        }

        if (!world.isOpaqueCube(pos,BlockDirection.DOWN)) {
            RenderBlock.renderSideDown(minX, maxX, minY, minZ, maxZ, getATexture(blockState,BlockDirection.DOWN,world))
            isRender = true
        }


        if (!world.isOpaqueCube(pos,BlockDirection.UP)) {
            RenderBlock.renderSideUp(minX, maxX, maxY, minZ, maxZ, getATexture(blockState,BlockDirection.UP,world))
            isRender = true
        }

        if (!world.isOpaqueCube(pos,BlockDirection.WEST)) {
            RenderBlock.renderSideWest(minX, minY, maxY, minZ, maxZ, getATexture(blockState,BlockDirection.WEST,world))
            isRender = true
        }

        if (!world.isOpaqueCube(pos,BlockDirection.EAST)) {
            RenderBlock.renderSideEast(maxX, minY, maxY, minZ, maxZ, getATexture(blockState,BlockDirection.EAST,world))
            isRender = true
        }
        isRender
    }

}
