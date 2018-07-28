package ru.megains.tartess.common.block

import ru.megains.tartess.common.block.data.{BlockDirection, BlockPos}
import ru.megains.tartess.common.entity.player.EntityPlayer
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.common.tileentity.TileEntityContainer
import ru.megains.tartess.common.world.World

abstract class BlockContainer(name:String) extends Block(name) with TileEntityContainer{

    override def isOpaqueCube: Boolean = false

    override def onBlockActivated(world: World, pos: BlockPos, player: EntityPlayer, itemStack: ItemPack, blockDirection: BlockDirection, float1: Float, float2: Float, float3: Float): Boolean = {
        if (world.getTileEntity(pos) == null) {
            false
        }else{
            player.openGui(world, pos)
            true
        }
   }

}
