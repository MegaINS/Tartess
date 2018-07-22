package ru.megains.tartess.block

import ru.megains.tartess.block.data.BlockState
import ru.megains.tartess.physics.{AABB, AABBs, BoundingBox, BoundingBoxes}
import ru.megains.tartess.tileentity.{TileEntity, TileEntityChest}
import ru.megains.tartess.world.World

import scala.collection.mutable

class BlockChest(name:String) extends BlockContainer(name){

    override def createNewTileEntity(worldIn: World, blockState: BlockState): TileEntity = new TileEntityChest(blockState.pos,worldIn)


    override val blockBodies: AABBs  = new AABBs(mutable.HashSet(new AABB(0.3f*16,0,0,1.7f*16,16,16)))

    override val boundingBoxes: BoundingBoxes = new BoundingBoxes(mutable.HashSet( new BoundingBox(32,16,16)))

}
