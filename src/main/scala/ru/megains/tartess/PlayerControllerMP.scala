package ru.megains.tartess

import ru.megains.tartess.block.Block
import ru.megains.tartess.block.data.{BlockDirection, BlockPos}
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.item.Item
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

        var result: EnumActionResult = EnumActionResult.PASS

        val item: Item = if (stack == null) null
        else stack.item
        val ret: EnumActionResult = if (item == null) EnumActionResult.PASS
        else item.onItemUseFirst(stack, player, worldIn, pos, facing, f, f1, f2)
        if (ret ne EnumActionResult.PASS) return ret
        val block: Block = worldIn.getBlock(pos).block
        flag = block.onBlockActivated(worldIn, pos, player, stack, facing, f, f1)
        if (flag) result = EnumActionResult.SUCCESS


        if (!flag) {
            if (stack == null){
                EnumActionResult.PASS
            } else {
                val j: Int = stack.stackSize
                val enumactionresult: EnumActionResult = stack.onItemUse(player, worldIn, tar.blockSelectPosition, facing, f, f1, f2)

                stack.stackSize = j
                enumactionresult
            }
        }
        else EnumActionResult.SUCCESS

    }

    def processRightClick(player: EntityPlayer, worldIn: World, stack: ItemStack): EnumActionResult = {
        syncCurrentPlayItem()

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

    }

    private def syncCurrentPlayItem() {
        val i: Int = tar.player.inventory.stackSelect
        if (i != currentPlayerItem) {
            currentPlayerItem = i
        }
    }
}
