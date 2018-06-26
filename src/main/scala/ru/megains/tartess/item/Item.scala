package ru.megains.tartess.item

import ru.megains.tartess.block.Block
import ru.megains.tartess.block.data.{BlockDirection, BlockPos, BlockState}
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.item.itemstack.ItemStack
import ru.megains.tartess.register.GameRegister
import ru.megains.tartess.renderer.texture.{TTextureRegister, TextureAtlas}
import ru.megains.tartess.utils.EnumActionResult.EnumActionResult
import ru.megains.tartess.utils.{ActionResult, EnumActionResult}
import ru.megains.tartess.world.World


class Item(val name: String) {


    var maxStackSize: Int = 64
    var mass:Int = 1
  //  val itemType:ItemType = ItemType.base
    var aTexture: TextureAtlas = _

    def registerTexture(textureRegister: TTextureRegister): Unit = {
        aTexture = textureRegister.registerTexture(name)
    }

    def onItemUse(stack: ItemStack, playerIn: EntityPlayer, worldIn: World, pos : BlockState, facing: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = EnumActionResult.PASS

    def onItemUseFirst(stack: ItemStack, player: EntityPlayer, world: World, pos: BlockPos, side: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = EnumActionResult.PASS

    def onItemRightClick(itemStackIn: ItemStack, worldIn: World, playerIn: EntityPlayer): ActionResult[ItemStack] = new ActionResult[ItemStack](EnumActionResult.PASS, itemStackIn)
}

object Item {


    def getItemById(id: Int): Item = GameRegister.getItemById(id)

    def getItemFromBlock(block: Block): Item = GameRegister.getItemFromBlock(block)

    def getIdFromItem(item: Item): Int = {
        GameRegister.getIdByItem(item)
    }


    def initItems(): Unit = {
        GameRegister.registerItem(1000, new Item("stick"))
    }

}
