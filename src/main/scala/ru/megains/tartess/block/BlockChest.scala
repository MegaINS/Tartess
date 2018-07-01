package ru.megains.tartess.block

import ru.megains.tartess.block.data.BlockState
import ru.megains.tartess.tileentity.{TileEntity, TileEntityChest}
import ru.megains.tartess.world.World

class BlockChest(name:String) extends BlockContainer(name){

    override def createNewTileEntity(worldIn: World, blockState: BlockState): TileEntity = new TileEntityChest(blockState.pos,worldIn)
}
