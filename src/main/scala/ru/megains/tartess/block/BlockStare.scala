package ru.megains.tartess.block
import ru.megains.tartess.block.data.BlockState
import ru.megains.tartess.physics.{AABB, BoundingBox}

class BlockStare(name:String) extends Block(name) {


    override val blockBody: AABB = new AABB(0,0,0,16,8,16)
    val blockBody1: AABB = new AABB(0,8,8,16,16,16)
    override val boundingBox: BoundingBox = new BoundingBox(16,8,16)

    val boundingBox1: BoundingBox = new BoundingBox(0,8,8,16,16,16)
    override def getSelectedBoundingBox(blockState: BlockState): Array[BoundingBox]  = getBoundingBox(blockState).map(_.sum(blockState.pos.x, blockState.pos.y, blockState.pos.z))

    override def getBoundingBox(blockState: BlockState): Array[BoundingBox] =  Array( boundingBox.rotate(blockState.blockDirection),boundingBox1.rotate(blockState.blockDirection))

    override def getBlockBody(state: BlockState):  Array[AABB] = Array(blockBody.rotate(state.blockDirection),blockBody1.rotate(state.blockDirection))

    override  def getSelectedBlockBody(blockState: BlockState):  Array[AABB]= getBlockBody(blockState).map(_.sum(blockState.pos.x, blockState.pos.y, blockState.pos.z))
}
