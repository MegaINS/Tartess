package ru.megains.tartess.server

import ru.megains.tartess.common.block.data.{BlockDirection, BlockPos, BlockState}
import ru.megains.tartess.common.entity.player.{EntityPlayer, GameType}
import ru.megains.tartess.common.item.{Item, ItemBlock}
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.common.network.packet.play.server.SPacketBlockChange
import ru.megains.tartess.common.utils.EnumActionResult.EnumActionResult
import ru.megains.tartess.common.utils.{ActionResult, EnumActionResult, RayTraceResult}
import ru.megains.tartess.common.world.World
import ru.megains.tartess.server.entity.EntityPlayerMP

class PlayerInteractionManager(world: World) {



    var thisPlayerMP: EntityPlayerMP = _
    var blockReachDistance: Double = 5.0d *16
    var gameType: GameType = GameType.CREATIVE
    var isDestroyingBlock: Boolean = false

    def setGameType(gameTypeIn: GameType): Unit = {
        gameType = gameTypeIn

    }






    def isCreative: Boolean = gameType.isCreative

    def onBlockClicked(pos: BlockPos, side: BlockDirection) {

        if (isCreative)  tryHarvestBlock(pos)

    }

    def tryHarvestBlock(pos: BlockPos): Boolean = {

        var flag1: Boolean = false
        if (isCreative) {
            flag1 = removeBlock(pos)
             thisPlayerMP.connection.sendPacket(new SPacketBlockChange(world, pos))
        }

        flag1

    }

    private def removeBlock(pos: BlockPos): Boolean = removeBlock(pos, canHarvest = false)

    private def removeBlock(pos: BlockPos, canHarvest: Boolean): Boolean = {
         val block: BlockState = world.getBlock(pos)
         val flag: Boolean = block.removedByPlayer(world, pos, thisPlayerMP, canHarvest)
         if (flag) block.onBlockDestroyedByPlayer(world, pos)
         flag

    }

    def blockRemoving(pos: BlockPos) {

    }


    def cancelDestroyingBlock() {
        isDestroyingBlock = false

    }

    def getBlockReachDistance: Double = blockReachDistance

    def processRightClick(player: EntityPlayer, worldIn: World, stack: ItemPack): EnumActionResult = {

        val i: Int = stack.stackSize

        val actionresult: ActionResult[ItemPack] = stack.useItemRightClick(worldIn, player)
        val itemstack: ItemPack = actionresult.result.asInstanceOf[ItemPack]

        player.setHeldItem(itemstack)
        if (this.isCreative) {
            itemstack.stackSize = i
        }
        if (itemstack.stackSize == 0) {
            player.setHeldItem(null)

        }
        actionresult.`type`

    }

    def processRightClickBlock(player: EntityPlayer, worldIn: World, stack: ItemPack, posMouseOver: BlockPos, posBlockSet: BlockState, facing: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = {

        val item: Item = if (stack == null) null
        else stack.item
        val ret: EnumActionResult = if (item == null) EnumActionResult.PASS
        else item.onItemUseFirst(stack, player, worldIn, posMouseOver, facing, hitX, hitY, hitZ)
        if (ret ne EnumActionResult.PASS) return ret
        val bypass: Boolean = true

        var result: EnumActionResult = EnumActionResult.PASS
        if (!player.isSneaking || bypass) {
            val block: BlockState = worldIn.getBlock(posMouseOver)
           if (block.onBlockActivated(worldIn, posMouseOver, player, stack, facing, hitX, hitY, hitZ)) result = EnumActionResult.SUCCESS
         }
        if (stack == null) EnumActionResult.PASS

        else {

            val block: BlockState = worldIn.getBlock(posMouseOver)
             if (isCreative) {
            val i: Int = stack.stackSize

                  val enumactionresult: EnumActionResult = stack.onItemUse(player, worldIn, posBlockSet, facing, hitX, hitY, hitZ)

             stack.stackSize = i
                 enumactionresult

                } else stack.onItemUse(player, worldIn, block, facing, hitX, hitY, hitZ)

        }

    }

    def processRightClickBlock(rayTrace: RayTraceResult): Unit = {


        val itemstack: ItemPack = thisPlayerMP.getHeldItem
        val block: BlockState = world.getBlock(rayTrace.blockPos)

        if (block.onBlockActivated(world, rayTrace.blockPos, thisPlayerMP, itemstack, rayTrace.sideHit, rayTrace.hitVec.x, rayTrace.hitVec.y, rayTrace.hitVec.z)){

        }else{
            itemstack.item match {
                case itemBlock:ItemBlock =>
                    val blockState = itemBlock.block.getSelectPosition(world, thisPlayerMP, rayTrace)
                    if(blockState!=null){
                        itemstack.onItemUse(thisPlayerMP, world,blockState, rayTrace.sideHit, rayTrace.hitVec.x, rayTrace.hitVec.y, rayTrace.hitVec.z)
                        thisPlayerMP.connection.sendPacket(new SPacketBlockChange(world, blockState.pos))
                    }

                case _ => itemstack.useItemRightClick(world, thisPlayerMP)
            }


        }


    }
}

