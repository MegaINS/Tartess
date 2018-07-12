package ru.megains.tartess.block


import ru.megains.tartess.physics.{AABB, BoundingBox}

class BlockTest(name:String,i:Int) extends Block(name){

    val blockBodys = Array(
        new AABB(0,0,0,15,15,15),
        new AABB(0,0,0,14,14,14),
        new AABB(0,0,0,13,13,13),
        new AABB(0,0,0,12,12,12),
        new AABB(0,0,0,11,11,11),
        new AABB(0,0,0,10,10,10),
        new AABB(0,0,0,9,9,9),
        new AABB(0,0,0,8,8,8),
        new AABB(0,0,0,7,7,7),
        new AABB(0,0,0,6,6,6),
        new AABB(0,0,0,5,5,5),
        new AABB(0,0,0,4,4,4),
        new AABB(0,0,0,3,3,3),
        new AABB(0,0,0,2,2,2),
        new AABB(0,0,0,1,1,1),

        new AABB(0,0,0,160,16,160),
        new AABB(0,0,0,16,160,16),
        new AABB(0,0,0,4,32,4)

    )
    val blockSizes = Array(
        new BoundingBox(15),
        new BoundingBox(14),
        new BoundingBox(13),
        new BoundingBox(12),
        new BoundingBox(11),
        new BoundingBox(10),
        new BoundingBox(9),
        new BoundingBox(8),
        new BoundingBox(7),
        new BoundingBox(6),
        new BoundingBox(5),
        new BoundingBox(4),
        new BoundingBox(3),
        new BoundingBox(2),
        new BoundingBox(1),

        new BoundingBox(160,16,160),
        new BoundingBox(16,160,16),
        new BoundingBox(4,32,4)

    )
    override val blockBody = blockBodys(i)

    override val boundingBox: BoundingBox =blockSizes(i)

}
