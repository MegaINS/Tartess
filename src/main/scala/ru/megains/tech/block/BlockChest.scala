package ru.megains.tech.block

//class BlockChest(name:String) extends BlockContainer(name){
//
//    override val blockBody: AxisAlignedBB =  new AxisAlignedBB(0,0,0,4,4,6)
//    override val blockSize: BlockSize = new BlockSize(4,4,6)
//
//    override  def getSelectedBoundingBox(blockState: BlockState): AxisAlignedBB = blockBody.rotate(blockState.blockDirection)
//
//
//    override def createNewTileEntity(worldIn: World, blockState: BlockState): TileEntity = new TileEntityChest(blockState.pos,worldIn)
//}