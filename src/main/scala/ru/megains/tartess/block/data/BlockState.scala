package ru.megains.tartess.block.data


import ru.megains.tartess.block.Block
import ru.megains.tartess.physics.{AABB, BoundingBox}
import ru.megains.tartess.utils.{RayTraceResult, Vec3f}
import ru.megains.tartess.world.World

class BlockState(val block: Block,val pos:BlockPos,val blockDirection:BlockDirection = BlockDirection.NONE) {


    def getBlockBody:AABB = block.getBlockBody(this)

    def getSelectedBlockBody:AABB = block.getSelectedBlockBody(this)

    def getBoundingBox: BoundingBox = block.getBoundingBox(this)

    def getSelectedBoundingBox: BoundingBox = block.getSelectedBoundingBox(this)

    def collisionRayTrace(world: World, start: Vec3f, end: Vec3f): RayTraceResult = block.collisionRayTrace(world,this, start, end)
}
