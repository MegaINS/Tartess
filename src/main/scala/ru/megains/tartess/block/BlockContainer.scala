package ru.megains.tartess.block

import ru.megains.tartess.block.data.{BlockDirection, BlockPos}
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.item.itemstack.ItemPack
import ru.megains.tartess.tileentity.TileEntityContainer
import ru.megains.tartess.world.World

abstract class BlockContainer(name:String) extends Block(name) with TileEntityContainer{

    override def isOpaqueCube: Boolean = false

    override def onBlockActivated(world: World, pos: BlockPos, player: EntityPlayer, itemStack: ItemPack, blockDirection: BlockDirection, float1: Float, float2: Float): Boolean = {
        if (world.getTileEntity(pos) == null) {
            false
        }else{
            player.openGui(world, pos)
            true
        }
   }

}
