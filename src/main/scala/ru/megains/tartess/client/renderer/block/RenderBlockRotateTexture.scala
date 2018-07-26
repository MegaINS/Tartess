package ru.megains.tartess.client.renderer.block

import ru.megains.tartess.client.renderer.api.TRenderBlock
import ru.megains.tartess.client.renderer.texture.{TTextureRegister, TextureAtlas}
import ru.megains.tartess.common.block.Block
import ru.megains.tartess.common.block.data.{BlockDirection, BlockState}
import ru.megains.tartess.common.physics.AABB
import ru.megains.tartess.common.world.World
import ru.megains.tartess.common.world.chunk.data.ChunkPosition

class RenderBlockRotateTexture(block:Block) extends TRenderBlock{

    val name = block.name


    var aTexture0: TextureAtlas = _
    var aTexture1: TextureAtlas = _
    var aTexture2: TextureAtlas = _
    var aTexture3: TextureAtlas = _
    var aTexture4: TextureAtlas = _
    var aTexture5: TextureAtlas = _


    override def getATexture(blockState: BlockState, blockDirection: BlockDirection, world: World): TextureAtlas ={

            blockDirection match {
                case BlockDirection.UP => aTexture0
                case BlockDirection.DOWN => aTexture5
                case BlockDirection.WEST =>
                    blockState.blockDirection match {
                        case BlockDirection.WEST => aTexture2
                        case BlockDirection.NORTH => aTexture3
                        case BlockDirection.SOUTH => aTexture4
                        case _=> aTexture1
                    }
                case BlockDirection.NORTH =>
                    blockState.blockDirection match {
                        case BlockDirection.WEST => aTexture4
                        case BlockDirection.NORTH => aTexture2
                        case BlockDirection.SOUTH => aTexture1
                        case _ => aTexture3
                    }
                case BlockDirection.SOUTH =>
                    blockState.blockDirection match {
                        case BlockDirection.WEST => aTexture3
                        case BlockDirection.NORTH => aTexture1
                        case BlockDirection.SOUTH => aTexture2
                        case _ => aTexture4
                    }
                case _ =>
                    blockState.blockDirection match {
                        case BlockDirection.WEST => aTexture1
                        case BlockDirection.NORTH => aTexture4
                        case BlockDirection.SOUTH => aTexture3
                        case _ => aTexture2
                    }
            }
        }

        override def registerTexture(textureRegister: TTextureRegister): Unit = {
            aTexture0 = textureRegister.registerTexture("0")
            aTexture1 = textureRegister.registerTexture("1")
            aTexture2 = textureRegister.registerTexture("2")
            aTexture3 = textureRegister.registerTexture("3")
            aTexture4 = textureRegister.registerTexture("4")
            aTexture5 = textureRegister.registerTexture("5")
        }






    override def render(blockState: BlockState, world: World, posWorld: ChunkPosition): Boolean = {

    var isRender = false

    val pos = blockState.pos
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

