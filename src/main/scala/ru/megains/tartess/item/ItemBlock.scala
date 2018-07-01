package ru.megains.tartess.item

import ru.megains.tartess.block.Block
import ru.megains.tartess.block.data.{BlockDirection, BlockState}
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.item.itemstack.ItemStack
import ru.megains.tartess.renderer.texture.TTextureRegister
import ru.megains.tartess.utils.EnumActionResult
import ru.megains.tartess.utils.EnumActionResult.EnumActionResult
import ru.megains.tartess.world.World

class ItemBlock(val block: Block) extends Item(block.name) {


    override def onItemUse(stack: ItemStack, playerIn: EntityPlayer, worldIn: World, pos: BlockState, facing: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = {

        if (placeBlockAt(stack, playerIn, worldIn, pos, facing, hitX, hitY, hitZ)) {
            stack.stackSize -= 1
        }
        EnumActionResult.SUCCESS
    }

    override def registerTexture(textureRegister: TTextureRegister): Unit = {}

    def placeBlockAt(stack: ItemStack, player: EntityPlayer, world: World, pos: BlockState, side: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
        if(pos == null) return false
        if (!world.setBlock(pos)) return false

        true
    }

}
