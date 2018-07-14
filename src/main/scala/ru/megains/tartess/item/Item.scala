package ru.megains.tartess.item

import ru.megains.tartess.block.Block
import ru.megains.tartess.block.data.{BlockDirection, BlockPos, BlockState}
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.item.ItemType.ItemType
import ru.megains.tartess.item.itemstack.ItemPack
import ru.megains.tartess.register.GameRegister
import ru.megains.tartess.renderer.texture.{TTextureRegister, TextureAtlas}
import ru.megains.tartess.utils.EnumActionResult.EnumActionResult
import ru.megains.tartess.utils.{ActionResult, EnumActionResult}
import ru.megains.tartess.world.World


abstract class Item(val name: String,val itemType: ItemType) {


    var maxStackSize: Int = 64
    val mass:Int = 1
    var aTexture: TextureAtlas = _

    def registerTexture(textureRegister: TTextureRegister): Unit = {
        aTexture = textureRegister.registerTexture(name)
    }

    def onItemUse(stack: ItemPack, playerIn: EntityPlayer, worldIn: World, pos : BlockState, facing: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = EnumActionResult.PASS

    def onItemUseFirst(stack: ItemPack, player: EntityPlayer, world: World, pos: BlockPos, side: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = EnumActionResult.PASS

    def onItemRightClick(itemStackIn: ItemPack, worldIn: World, playerIn: EntityPlayer): ActionResult[ItemPack] = new ActionResult[ItemPack](EnumActionResult.PASS, itemStackIn)
}

object Item {


    def getItemById(id: Int): Item = GameRegister.getItemById(id)

    def getItemFromBlock(block: Block): Item = GameRegister.getItemFromBlock(block)

    def getIdFromItem(item: Item): Int = {
        GameRegister.getIdByItem(item)
    }



}
