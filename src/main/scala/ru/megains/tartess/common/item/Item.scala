package ru.megains.tartess.common.item

import ru.megains.tartess.common.block.Block
import ru.megains.tartess.common.block.data.{BlockDirection, BlockState}
import ru.megains.tartess.common.entity.player.EntityPlayer
import ru.megains.tartess.common.item.ItemType.ItemType
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.common.register.GameRegister
import ru.megains.tartess.common.utils.EnumActionResult
import ru.megains.tartess.common.utils.EnumActionResult.EnumActionResult
import ru.megains.tartess.common.world.World


abstract class Item(val name: String,val itemType: ItemType) {


    var maxStackSize: Int = 64
    val mass:Int = 1

    def onItemUse(stack: ItemPack, playerIn: EntityPlayer, worldIn: World, pos : BlockState, facing: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = EnumActionResult.PASS

}

object Item {


    def getItemById(id: Int): Item = GameRegister.getItemById(id)

    def getItemFromBlock(block: Block): Item = GameRegister.getItemFromBlock(block)

    def getIdFromItem(item: Item): Int = {
        GameRegister.getIdByItem(item)
    }



}
