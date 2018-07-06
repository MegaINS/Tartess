package ru.megains.tartess.block

import ru.megains.tartess.block.data.{BlockState}
import ru.megains.tartess.physics.{AABB, BoundingBox}
import ru.megains.tartess.tileentity.{TileEntity, TileEntityChest}
import ru.megains.tartess.world.World

class BlockChest(name:String) extends BlockContainer(name){

    override def createNewTileEntity(worldIn: World, blockState: BlockState): TileEntity = new TileEntityChest(blockState.pos,worldIn)

    override val blockBody: AABB = new AABB(0.3f,0,0,1.7f,1,1)
    override val boundingBox: BoundingBox = new BoundingBox(32,16,16)
}
