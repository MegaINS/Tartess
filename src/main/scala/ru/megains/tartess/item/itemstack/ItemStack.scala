package ru.megains.tartess.item.itemstack

import ru.megains.tartess.block.Block
import ru.megains.tartess.block.data.{BlockDirection, BlockState}
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.item.Item
import ru.megains.tartess.utils.ActionResult
import ru.megains.tartess.utils.EnumActionResult.EnumActionResult
import ru.megains.tartess.world.World


class ItemStack (val item: Item, var stackSize:Int = 1) {


    def this(block: Block, size:Int) = this(Item.getItemFromBlock(block), size)

    def this(block: Block) = this(block,1)

    def splitStack(size: Int): ItemStack = {

        stackSize -= size
        new ItemStack(item, size)
    }

    def onItemUse(playerIn: EntityPlayer, worldIn: World, pos: BlockState, side: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = {
        val enumactionresult: EnumActionResult = item.onItemUse(this, playerIn, worldIn, pos, side, hitX, hitY, hitZ)
        enumactionresult
    }

    def useItemRightClick(worldIn: World, playerIn: EntityPlayer): ActionResult[ItemStack] = item.onItemRightClick(this, worldIn, playerIn)

}
