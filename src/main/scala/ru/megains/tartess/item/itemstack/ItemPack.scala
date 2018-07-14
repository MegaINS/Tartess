package ru.megains.tartess.item.itemstack

import ru.megains.tartess.block.Block
import ru.megains.tartess.block.data.{BlockDirection, BlockState}
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.item._
import ru.megains.tartess.utils.ActionResult
import ru.megains.tartess.utils.EnumActionResult.EnumActionResult
import ru.megains.tartess.world.World


class ItemPack private(val item: Item, var stackSize:Int, var stackMass:Int) {

    def this(block: Block) = this(Item.getItemFromBlock(block), 1, block.mass)

    def this(block: Block, size:Int) = this(Item.getItemFromBlock(block), size,block.mass * size)

    def this(item: Item) = this(item, 1,item.mass)

    def this(item: Item,sizeOrMass:Int) ={
        this(item,
            item.itemType match {
                case ItemType.STACK => sizeOrMass
                case ItemType.MASS | ItemType.SINGLE => 1
            },
            item.itemType match {
                case ItemType.STACK => item.mass * sizeOrMass
                case ItemType.MASS => sizeOrMass
                case ItemType.SINGLE => 1
            })
    }

    def splitStack(size: Int): ItemPack = {

        item.itemType match {
            case ItemType.STACK | ItemType.SINGLE  =>
                stackSize -= size
                stackMass -= item.mass * size
                new ItemPack(item, size)
            case ItemType.MASS =>
                stackMass -= item.mass * size
                new ItemPack(item, size)
        }
    }

    def onItemUse(playerIn: EntityPlayer, worldIn: World, pos: BlockState, side: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = {

        val enumactionresult: EnumActionResult = item.onItemUse(this, playerIn, worldIn, pos, side, hitX, hitY, hitZ)
        enumactionresult
    }

    def useItemRightClick(worldIn: World, playerIn: EntityPlayer): ActionResult[ItemPack] = item.onItemRightClick(this, worldIn, playerIn)

}
