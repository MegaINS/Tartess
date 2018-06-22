package ru.megains.old.graph.renderer.block

import ru.megains.old.block.Block
import ru.megains.old.blockdata.BlockWorldPos
import ru.megains.old.graph.renderer.api.ARenderBlock
import ru.megains.old.register.Blocks
import ru.megains.old.world.World
import ru.megains.tartess.block.data.BlockDirection


object RenderBlockGlass extends ARenderBlock{
    override def render(block: Block, world: World, posWorld: BlockWorldPos, posRender: BlockWorldPos/*,offset:MultiBlockPos*/): Unit = {

        val AABB = block.getSelectedBoundingBox(posWorld/*,offset*/).sum(posRender.worldX,posRender.worldY,posRender.worldZ)
        val minX = AABB.getMinX
        val minY = AABB.getMinY
        val minZ = AABB.getMinZ
        val maxX = AABB.getMaxX
        val maxY = AABB.getMaxY
        val maxZ = AABB.getMaxZ
        var block1:Block = null




        block1 = world.getBlock(posWorld.sum(BlockDirection.SOUTH))
        if (!block1.isOpaqueCube&&block1!=Blocks.glass) {
            RenderBlock.renderSideSouth(minX,maxX,minY,maxY,maxZ,block.getATexture(BlockDirection.SOUTH))
        }
        block1 = world.getBlock(posWorld.sum(BlockDirection.NORTH))
        if (!block1.isOpaqueCube&&block1!=Blocks.glass) {
            RenderBlock.renderSideNorth(minX,maxX,minY,maxY,minZ,block.getATexture(BlockDirection.NORTH))
        }
        block1 = world.getBlock(posWorld.sum(BlockDirection.DOWN))
        if (!block1.isOpaqueCube&&block1!=Blocks.glass) {
            RenderBlock.renderSideDown(minX,maxX,minY,minZ,maxZ,block.getATexture(BlockDirection.DOWN))
        }
        block1 = world.getBlock(posWorld.sum(BlockDirection.UP))
        if (!block1.isOpaqueCube&&block1!=Blocks.glass) {
            RenderBlock.renderSideUp(minX,maxX,maxY,minZ,maxZ,block.getATexture(BlockDirection.UP))
        }
        block1 = world.getBlock(posWorld.sum(BlockDirection.WEST))
        if (!block1.isOpaqueCube&&block1!=Blocks.glass) {
            RenderBlock.renderSideWest(minX,minY,maxY,minZ,maxZ,block.getATexture(BlockDirection.WEST))
        }
        block1 = world.getBlock(posWorld.sum(BlockDirection.EAST))
        if (!block1.isOpaqueCube&&block1!=Blocks.glass) {
            RenderBlock.renderSideEast(maxX,minY,maxY,minZ,maxZ,block.getATexture(BlockDirection.EAST))
        }



    }
}
