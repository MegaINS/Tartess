package ru.megains.tartess.item.itemstack

import ru.megains.tartess.block.Block
import ru.megains.tartess.block.data.{BlockDirection, BlockState}
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.item.Item
import ru.megains.tartess.utils.ActionResult
import ru.megains.tartess.utils.EnumActionResult.EnumActionResult
import ru.megains.tartess.world.World


class ItemStack (val item: Item, var stackSize:Int = 1/*, var stackMass:Int*/) {


//    def this(item: Item/*,sizeOrMass:Int*/) ={
//        this(item,1)
//          //  if(item.itemType == ItemType.base ) sizeOrMass else 1,
//           // if(item.itemType == ItemType.base ) item.mass * sizeOrMass else sizeOrMass)
//    }




   // def this(item: Item) = this(item,1,item.mass)



    def this(block: Block, size:Int) = this(Item.getItemFromBlock(block), size/*,Item.getItemFromBlock(block).mass * size */)

    def this(block: Block) = this(block,1)


    def splitStack(size: Int): ItemStack = {

       // item.itemType match {
          //  case ItemType.base | ItemType.single  =>
                stackSize -= size
               // stackMass -= item.mass * size
                new ItemStack(item, size)
           // case ItemType.mass =>
                //stackMass -= item.mass * size
              //  new ItemStack(item, size)
       // }
    }



    def onItemUse(playerIn: EntityPlayer, worldIn: World, pos: BlockState, side: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = {
        // if (!worldIn.isRemote) return net.minecraftforge.common.ForgeHooks.onPlaceItemIntoWorld(this, playerIn, worldIn, pos, side, hitX, hitY, hitZ)
        val enumactionresult: EnumActionResult = item.onItemUse(this, playerIn, worldIn, pos, side, hitX, hitY, hitZ)
        //  if (enumactionresult eq EnumActionResult.SUCCESS) playerIn.addStat(StatList.getObjectUseStats(this.item))
        enumactionresult
    }

    def useItemRightClick(worldIn: World, playerIn: EntityPlayer): ActionResult[ItemStack] = item.onItemRightClick(this, worldIn, playerIn)



}
