package ru.megains.tartess.server

import ru.megains.tartess.common.block.data.{BlockDirection, BlockPos}
import ru.megains.tartess.common.entity.player.{EntityPlayer, GameType}
import ru.megains.tartess.common.item.Item
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.common.utils.EnumActionResult.EnumActionResult
import ru.megains.tartess.common.utils.{ActionResult, EnumActionResult}
import ru.megains.tartess.common.world.World
import ru.megains.tartess.server.entity.EntityPlayerMP

class PlayerInteractionManager(world: World) {

    var thisPlayerMP: EntityPlayerMP = _
    var blockReachDistance: Double = 5.0d
    var gameType: GameType = GameType.CREATIVE
    var isDestroyingBlock: Boolean = false

    def setGameType(gameTypeIn: GameType): Unit = {
        gameType = gameTypeIn
        // gameTypeIn.configurePlayerCapabilities(this.thisPlayerMP.capabilities)
        //  thisPlayerMP.sendPlayerAbilities()
        //  thisPlayerMP.ocServer.playerList.sendPacketToAllPlayers(new SPacketPlayerListItem(SPacketPlayerListItem.Action.UPDATE_GAME_MODE, thisPlayerMP))
        //theWorld.updateAllPlayersSleepingFlag()
    }






    def isCreative: Boolean = gameType.isCreative

    def onBlockClicked(pos: BlockPos, side: BlockDirection) {
        //        val event: PlayerInteractEvent.LeftClickBlock = net.minecraftforge.common.ForgeHooks.onLeftClickBlock(thisPlayerMP, pos, side, net.minecraftforge.common.ForgeHooks.rayTraceEyeHitVec(thisPlayerMP, getBlockReachDistance + 1))
        //        if (event.isCanceled) {
        //            // Restore block and te data
        //            thisPlayerMP.connection.sendPacket(new SPacketBlockChange(theWorld, pos))
        //            theWorld.notifyBlockUpdate(pos, theWorld.getBlockState(pos), theWorld.getBlockState(pos), 3)
        //            return
        //        }
        if (isCreative) /* if (!theWorld.extinguishFire(null.asInstanceOf[EntityPlayer], pos, side))*/ tryHarvestBlock(pos)
        //        else {
        //            val iblockstate: IBlockState = theWorld.getBlockState(pos)
        //            val block: Block = iblockstate.getBlock
        //            if (gameType.isAdventure) {
        //                if (gameType eq GameType.SPECTATOR) return
        //                if (!thisPlayerMP.isAllowEdit) {
        //                    val itemstack: ItemStack = thisPlayerMP.getHeldItemMainhand
        //                    if (itemstack == null) return
        //                    if (!itemstack.canDestroy(block)) return
        //                }
        //            }
        //            initialDamage = curblockDamage
        //            var f: Float = 1.0F
        //            if (!iblockstate.getBlock.isAir(iblockstate, theWorld, pos)) {
        //                if (event.getUseBlock ne net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) {
        //                    block.onBlockClicked(theWorld, pos, thisPlayerMP)
        //                    theWorld.extinguishFire(null.asInstanceOf[EntityPlayer], pos, side)
        //                }
        //                else {
        //                    // Restore block and te data
        //                    thisPlayerMP.connection.sendPacket(new SPacketBlockChange(theWorld, pos))
        //                    theWorld.notifyBlockUpdate(pos, theWorld.getBlockState(pos), theWorld.getBlockState(pos), 3)
        //                }
        //                f = iblockstate.getPlayerRelativeBlockHardness(thisPlayerMP, thisPlayerMP.worldObj, pos)
        //            }
        //            if (event.getUseItem eq net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) {
        //                if (f >= 1.0F) {
        //                    // Restore block and te data
        //                    thisPlayerMP.connection.sendPacket(new SPacketBlockChange(theWorld, pos))
        //                    theWorld.notifyBlockUpdate(pos, theWorld.getBlockState(pos), theWorld.getBlockState(pos), 3)
        //                }
        //                return
        //            }
        //            if (!iblockstate.getBlock.isAir(iblockstate, theWorld, pos) && f >= 1.0F) tryHarvestBlock(pos)
        //            else {
        //                isDestroyingBlock = true
        //                destroyPos = pos
        //                val i: Int = (f * 10.0F).toInt
        //                theWorld.sendBlockBreakProgress(thisPlayerMP.getEntityId, pos, i)
        //                durabilityRemainingOnBlock = i
        //            }
        //        }
    }

    def tryHarvestBlock(pos: BlockPos): Boolean = {
        //  val exp: Int = net.minecraftforge.common.ForgeHooks.onBlockBreakEvent(theWorld, gameType, thisPlayerMP, pos)
        //  if (exp == -1) false
        //  else {
        // val iblockstate: IBlockState = theWorld.getBlockState(pos)
        //  val tileentity: TileEntity = theWorld.getTileEntity(pos)
        //  val block: Block = iblockstate.getBlock
        //            if ((block.isInstanceOf[BlockCommandBlock] || block.isInstanceOf[BlockStructure]) && !thisPlayerMP.func_189808_dh) {
        //                theWorld.notifyBlockUpdate(pos, iblockstate, iblockstate, 3)
        //                false
        //            }
        //            else {
        //  val stack: ItemStack = thisPlayerMP.getHeldItemMainhand
        //  if (stack != null && stack.item.onBlockStartBreak(stack, pos, thisPlayerMP)) return false
        //    theWorld.playEvent(thisPlayerMP, 2001, pos, Block.getStateId(iblockstate))
        var flag1: Boolean = false
        if (isCreative) {
            flag1 = removeBlock(pos)
            //todo thisPlayerMP.connection.sendPacket(new SPacketBlockChange(world, pos))
        }
        //                else {
        //                    val itemstack1: ItemStack = thisPlayerMP.getHeldItemMainhand
        //                    val itemstack2: ItemStack = if (itemstack1 == null) null
        //                    else itemstack1.copy
        //                    val flag: Boolean = iblockstate.getBlock.canHarvestBlock(theWorld, pos, thisPlayerMP)
        //                    if (itemstack1 != null) {
        //                        itemstack1.onBlockDestroyed(theWorld, iblockstate, pos, thisPlayerMP)
        //                        if (itemstack1.stackSize <= 0) {
        //                            net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(thisPlayerMP, itemstack1, EnumHand.MAIN_HAND)
        //                            thisPlayerMP.setHeldItem(EnumHand.MAIN_HAND, null.asInstanceOf[ItemStack])
        //                        }
        //                    }
        //                    flag1 = removeBlock(pos, flag)
        //                    if (flag1 && flag) iblockstate.getBlock.harvestBlock(theWorld, thisPlayerMP, pos, iblockstate, tileentity, itemstack2)
        //                }
        // Drop experience
        //  if (!isCreative && flag1 && exp > 0) iblockstate.getBlock.dropXpOnBlockBreak(theWorld, pos, exp)
        flag1
        //   }
        //  }
    }

    private def removeBlock(pos: BlockPos): Boolean = removeBlock(pos, canHarvest = false)

    private def removeBlock(pos: BlockPos, canHarvest: Boolean): Boolean = {
        //todo val block: Block = world.getBlock(pos)
        //todo val flag: Boolean = block.removedByPlayer(world, pos, thisPlayerMP, canHarvest)
        //todo if (flag) block.onBlockDestroyedByPlayer(world, pos)
        //todo flag
        false//todo
    }

    def blockRemoving(pos: BlockPos) {
        //        if (pos == destroyPos) {
        //            val i: Int = curblockDamage - initialDamage
        //            val iblockstate: IBlockState = theWorld.getBlockState(pos)
        //            if (!iblockstate.getBlock.isAir(iblockstate, theWorld, pos)) {
        //                val f: Float = iblockstate.getPlayerRelativeBlockHardness(thisPlayerMP, thisPlayerMP.worldObj, pos) * (i + 1).toFloat
        //                if (f >= 0.7F) {
        //                    isDestroyingBlock = false
        //                    theWorld.sendBlockBreakProgress(thisPlayerMP.getEntityId, pos, -1)
        //                    tryHarvestBlock(pos)
        //                }
        //                else if (!receivedFinishDiggingPacket) {
        //                    isDestroyingBlock = false
        //                    receivedFinishDiggingPacket = true
        //                    delayedDestroyPos = pos
        //                    initialBlockDamage = initialDamage
        //                }
        //            }
        //        }
    }


    def cancelDestroyingBlock() {
        isDestroyingBlock = false
        //  world.sendBlockBreakProgress(thisPlayerMP.getEntityId, destroyPos, -1)
    }

    def getBlockReachDistance: Double = blockReachDistance

    def processRightClick(player: EntityPlayer, worldIn: World, stack: ItemPack): EnumActionResult = {
        //  if (this.gameType eq GameType.SPECTATOR) EnumActionResult.PASS
        //   else
        //  if (player.getCooldownTracker.hasCooldown(stack.item)) EnumActionResult.PASS
        //   else {
        val i: Int = stack.stackSize
        // val j: Int = stack.getMetadata
        val actionresult: ActionResult[ItemPack] = stack.useItemRightClick(worldIn, player)
        val itemstack: ItemPack = actionresult.result.asInstanceOf[ItemPack]
        //  if ((itemstack eq stack) && itemstack.stackSize == i && itemstack.getMaxItemUseDuration <= 0 && itemstack.getMetadata == j) actionresult.`type`
        //  else {
        player.setHeldItem(itemstack)
        if (this.isCreative) {
            itemstack.stackSize = i
            //  if (itemstack.isItemStackDamageable) itemstack.setItemDamage(j)
        }
        if (itemstack.stackSize == 0) {
            player.setHeldItem(null)

        }
        //  if (!player.isHandActive) player.asInstanceOf[EntityPlayerMP].sendContainerToPlayer(player.inventoryContainer)
        actionresult.`type`
        //   }
        //  }
    }

    def processRightClickBlock(player: EntityPlayer, worldIn: World, stack: ItemPack, posMouseOver: BlockPos, posBlockSet: BlockPos, facing: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = {
        //        if (this.gameType eq GameType.SPECTATOR) {
        //            val tileentity: TileEntity = worldIn.getTileEntity(pos)
        //            if (tileentity.isInstanceOf[ILockableContainer]) {
        //                val block1: Block = worldIn.getBlockState(pos).getBlock
        //                var ilockablecontainer: ILockableContainer = tileentity.asInstanceOf[ILockableContainer]
        //                if (ilockablecontainer.isInstanceOf[TileEntityChest] && block1.isInstanceOf[BlockChest]) ilockablecontainer = block1.asInstanceOf[BlockChest].getLockableContainer(worldIn, pos)
        //                if (ilockablecontainer != null) {
        //                    player.displayGUIChest(ilockablecontainer)
        //                    return EnumActionResult.SUCCESS
        //                }
        //            }
        //            else if (tileentity.isInstanceOf[IInventory]) {
        //                player.displayGUIChest(tileentity.asInstanceOf[IInventory])
        //                return EnumActionResult.SUCCESS
        //            }
        //            EnumActionResult.PASS
        //        }
        //        else {

        val item: Item = if (stack == null) null
        else stack.item
        val ret: EnumActionResult = if (item == null) EnumActionResult.PASS
        else item.onItemUseFirst(stack, player, worldIn, posMouseOver, facing, hitX, hitY, hitZ)
        if (ret ne EnumActionResult.PASS) return ret
        val bypass: Boolean = true
        ///   for (s <- Array[ItemStack](player.getHeldItemMainhand, player.getHeldItemOffhand))
        //    bypass = bypass && (s == null || s.item.doesSneakBypassUse(s, worldIn, pos, player))
        var result: EnumActionResult = EnumActionResult.PASS
        //todoif (!player.isSneaking || bypass) {
        //todo    val block: Block = worldIn.getBlock(posMouseOver)
        //todo   if (block.onBlockActivated(worldIn, posMouseOver, player, stack, facing, hitX, hitY, hitZ)) result = EnumActionResult.SUCCESS
        //todo }
        if (stack == null) EnumActionResult.PASS
        // else if (player.getCooldownTracker.hasCooldown(stack.item)) EnumActionResult.PASS
        else {
            //                if (stack.item.isInstanceOf[ItemBlock] && !player.func_189808_dh) {
            //                    val block: Block = stack.item.asInstanceOf[ItemBlock].getBlock
            //                    if (block.isInstanceOf[BlockCommandBlock] || block.isInstanceOf[BlockStructure]) return EnumActionResult.FAIL
            //                }
            //todo if (isCreative) {
                //  val j: Int = stack.getMetadata
            //todo  val i: Int = stack.stackSize

                //todo  val enumactionresult: EnumActionResult = stack.onItemUse(player, worldIn, posBlockSet, facing, hitX, hitY, hitZ)
                //  stack.setItemDamage(j)
            //todo  stack.stackSize = i
                //todo enumactionresult

                //todo } else stack.onItemUse(player, worldIn, posBlockSet, facing, hitX, hitY, hitZ)
        null//todo
        }
        // }
    }


}

