package ru.megains.tartess.block

import ru.megains.tartess.physics.AABB

class BlockGlass(name:String) extends Block(name){

    override val blockBody: AABB = Block.FULL_AABB

    override def isOpaqueCube: Boolean = false

}
