package ru.megains.tartess.common.block.data


import ru.megains.tartess.common.block.Block
import ru.megains.tartess.common.entity.player.EntityPlayer
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.common.physics.{AABBs, BoundingBoxes}
import ru.megains.tartess.common.utils.{RayTraceResult, Vec3f}
import ru.megains.tartess.common.world.World

class BlockState(val block: Block,val pos:BlockPos,val blockDirection:BlockDirection = BlockDirection.NONE) {

    def getBlockBody:AABBs  = block.getBlockBody(this)

    def getSelectedBlockBody:AABBs = block.getSelectedBlockBody(this)

    def getBoundingBox:BoundingBoxes  = block.getBoundingBox(this)

    def getSelectedBoundingBox:BoundingBoxes  = block.getSelectedBoundingBox(this)

    def collisionRayTrace(world: World, start: Vec3f, end: Vec3f): RayTraceResult = block.collisionRayTrace(world,this, start, end)

    def onBlockActivated(worldIn: World, pos: BlockPos, playerIn: EntityPlayer, heldItem: ItemPack, side: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): Boolean = block.onBlockActivated(worldIn,pos,playerIn,heldItem,side,hitX,hitY,hitZ )

    def removedByPlayer(world: World, pos: BlockPos, player: EntityPlayer, willHarvest: Boolean): Boolean = block.removedByPlayer(world, pos, player, willHarvest)

    def onBlockHarvested(worldIn: World, pos: BlockPos, player: EntityPlayer): Unit =  block.onBlockHarvested(worldIn, pos, player)

    def onBlockDestroyedByPlayer(worldIn: World, pos: BlockPos): Unit = block.onBlockDestroyedByPlayer(worldIn, pos)

}
