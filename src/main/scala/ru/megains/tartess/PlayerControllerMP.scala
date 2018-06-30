package ru.megains.tartess

import ru.megains.tartess.block.Block
import ru.megains.tartess.block.data.{BlockDirection, BlockPos}
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.item.{Item, ItemBlock}
import ru.megains.tartess.item.itemstack.ItemStack
import ru.megains.tartess.utils.EnumActionResult.EnumActionResult
import ru.megains.tartess.utils.{ActionResult, EnumActionResult, Vec3f}
import ru.megains.tartess.world.World

class PlayerControllerMP(tar:Tartess) {

    var isHittingBlock: Boolean = false
    var blockHitDelay: Int = 0
    var currentPlayerItem: Int = 0

    def processRightClickBlock(player: EntityPlayer, worldIn: World, stack: ItemStack, pos: BlockPos, facing: BlockDirection, vec: Vec3f): EnumActionResult = {

        syncCurrentPlayItem()
        val f: Float = (vec.x - pos.x.toDouble).toFloat
        val f1: Float = (vec.y - pos.y.toDouble).toFloat
        val f2: Float = (vec.z - pos.z.toDouble).toFloat
        var flag: Boolean = false
        //    if (!this.mc.theWorld.getWorldBorder.contains(pos)) EnumActionResult.FAIL
        //   else {

        var result: EnumActionResult = EnumActionResult.PASS
        // if (currentGameType ne GameType.SPECTATOR) {
        val item: Item = if (stack == null) null
        else stack.item
        val ret: EnumActionResult = if (item == null) EnumActionResult.PASS
        else item.onItemUseFirst(stack, player, worldIn, pos, facing, f, f1, f2)
        if (ret ne EnumActionResult.PASS) return ret
        val block: Block = worldIn.getBlock(pos).block
        val bypass: Boolean = true
        //                for (s <- Array[ItemStack](player.getHeldItemMainhand, player.getHeldItemOffhand)) //TODO: Expand to more hands? player.inv.getHands()?
        //                    bypass = bypass && (s == null || s.item.doesSneakBypassUse(s, worldIn, pos, player))
       // if (!player.isSneaking || bypass) {
            flag = block.onBlockActivated(worldIn, pos, player, stack, facing, f, f1)
            if (flag) result = EnumActionResult.SUCCESS
       // }
        if (!flag && stack != null && stack.item.isInstanceOf[ItemBlock]) {
            val itemblock: ItemBlock = stack.item.asInstanceOf[ItemBlock]
            // if (!itemblock.canPlaceBlockOnSide(worldIn, pos, facing, player, stack)) return EnumActionResult.FAIL
        }
        // }
        // connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, oc.blockSelectPosition, facing, f, f1, f2))
        if (!flag /*&& (currentGameType ne GameType.SPECTATOR)*/) {
            if (stack == null){
                EnumActionResult.PASS
                //  else if (player.getCooldownTracker.hasCooldown(stack.item)) EnumActionResult.PASS
            } else {
                //                if (stack.item.isInstanceOf[ItemBlock] && !player.func_189808_dh) {
                //                 //  val block: Block = stack.item.asInstanceOf[ItemBlock].block
                //                   // if (block.isInstanceOf[BlockCommandBlock] || block.isInstanceOf[BlockStructure]) return EnumActionResult.FAIL
                //                }
                // if (currentGameType.isCreative) {
                //val i: Int = stack.getMetadata
                val j: Int = stack.stackSize
                val enumactionresult: EnumActionResult = stack.onItemUse(player, worldIn, tar.blockSelectPosition, facing, f, f1, f2)
                //    stack.setItemDamage(i)
                stack.stackSize = j
                enumactionresult

                //  }
                // else {
                //     stack.onItemUse(player, worldIn, oc.blockSelectPosition, facing, f, f1, f2)
                // }
            }
        }
        else EnumActionResult.SUCCESS
        //  }
    }

    def processRightClick(player: EntityPlayer, worldIn: World, stack: ItemStack): EnumActionResult = //if (this.currentGameType eq GameType.SPECTATOR) EnumActionResult.PASS
    //  else
    {
        syncCurrentPlayItem()
        //  connection.sendPacket(new CPacketPlayerTryUseItem())
        // if (player.getCooldownTracker.hasCooldown(stack.item)) EnumActionResult.PASS
        //  else {
        val i: Int = stack.stackSize
        val actionresult: ActionResult[ItemStack] = stack.useItemRightClick(worldIn, player)
        val itemstack: ItemStack = actionresult.result
        if ((itemstack ne stack) || itemstack.stackSize != i) {
            player.setHeldItem(itemstack)
            if (itemstack.stackSize <= 0) {
                player.setHeldItem(null)
            }
        }
        actionresult.`type`
        // }
    }

    private def syncCurrentPlayItem() {
        val i: Int = tar.player.inventory.stackSelect
        if (i != currentPlayerItem) {
            currentPlayerItem = i
            //  connection.sendPacket(new CPacketHeldItemChange(currentPlayerItem))
        }
    }
}
