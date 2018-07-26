package ru.megains.tartess.common.block

import ru.megains.tartess.common.block.data.BlockState
import ru.megains.tartess.common.physics.{AABB, AABBs, BoundingBox, BoundingBoxes}
import ru.megains.tartess.common.tileentity.{TileEntity, TileEntityChest}
import ru.megains.tartess.common.world.World

import scala.collection.mutable

class BlockChest(name:String) extends BlockContainer(name){

    override def createNewTileEntity(worldIn: World, blockState: BlockState): TileEntity = new TileEntityChest(blockState.pos,worldIn)


    override val blockBodies: AABBs  = new AABBs(mutable.HashSet(new AABB(0,0,0.3f*16,16,16,1.7f*16)))

    override val boundingBoxes: BoundingBoxes = new BoundingBoxes(mutable.HashSet( new BoundingBox(16,16,32)))

}
