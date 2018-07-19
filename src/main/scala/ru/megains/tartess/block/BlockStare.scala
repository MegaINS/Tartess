package ru.megains.tartess.block
import ru.megains.tartess.block.data.BlockState
import ru.megains.tartess.physics.{AABB, BoundingBox}

class BlockStare(name:String,x:Int) extends Block(name) {



    val blockBody1: Array[AABB] = Array(
        new AABB(0,0,0,16,8,16),
        new AABB(0,0,0,32,8,16)
    )
    val blockBody2: Array[AABB] = Array(
        new AABB(0,8,8,16,16,16),
        new AABB(0,8,8,32,16,16)

    )
    override val blockBody: AABB =blockBody1(x)



    val boundingBox1:  Array[BoundingBox] =  Array(
        new BoundingBox(16,8,16),
        new BoundingBox(32,8,16)

    )
    val boundingBox2:  Array[BoundingBox] = Array(
        new BoundingBox(0,8,8,16,16,16),
        new BoundingBox(0,8,8,32,16,16)
    )
    override val boundingBox: BoundingBox = boundingBox1(x)
    override def getSelectedBoundingBox(blockState: BlockState): Array[BoundingBox]  = getBoundingBox(blockState).map(_.sum(blockState.pos.x, blockState.pos.y, blockState.pos.z))

    override def getBoundingBox(blockState: BlockState): Array[BoundingBox] =  Array( boundingBox1(x).rotate(blockState.blockDirection),boundingBox2(x).rotate(blockState.blockDirection))

    override def getBlockBody(state: BlockState):  Array[AABB] = Array(blockBody1(x).rotate(state.blockDirection),blockBody2(x).rotate(state.blockDirection))

    override  def getSelectedBlockBody(blockState: BlockState):  Array[AABB]= getBlockBody(blockState).map(_.sum(blockState.pos.x, blockState.pos.y, blockState.pos.z))
}
