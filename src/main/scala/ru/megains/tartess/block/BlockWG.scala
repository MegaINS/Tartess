package ru.megains.tartess.block


import ru.megains.tartess.physics.{AABB, BoundingBox}

class BlockWG(name:String) extends Block(name) {

    override val blockBody:AABB = new AABB(16)
    override val boundingBox: BoundingBox = new BoundingBox(16)
}
