package ru.megains.tartess.common.item

import ru.megains.tartess.common.block.Block
import ru.megains.tartess.common.block.data.{BlockDirection, BlockState}
import ru.megains.tartess.common.entity.player.EntityPlayer
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.common.utils.EnumActionResult
import ru.megains.tartess.common.utils.EnumActionResult.EnumActionResult
import ru.megains.tartess.common.world.World

class ItemBlock(val block: Block) extends ItemStack(block.name) {


    override def onItemUse(stack: ItemPack, playerIn: EntityPlayer, worldIn: World, pos: BlockState, facing: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = {

        if (placeBlockAt(stack, playerIn, worldIn, pos, facing, hitX, hitY, hitZ)) {
           // stack.stackSize -= 1
        }
        EnumActionResult.SUCCESS
    }

    def placeBlockAt(stack: ItemPack, player: EntityPlayer, world: World, pos: BlockState, side: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
        if(pos == null) return false
        if (!world.setBlock(pos)) return false

        true
    }

}
