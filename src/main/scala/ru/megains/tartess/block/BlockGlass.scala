package ru.megains.tartess.block


import ru.megains.tartess.physics.{AABB, BoundingBox}

class BlockGlass(name:String) extends Block(name){


    override val blockBody: AABB = new AABB(1)
    override val boundingBox: BoundingBox = new BoundingBox(16)
    override def isOpaqueCube: Boolean = false


}
