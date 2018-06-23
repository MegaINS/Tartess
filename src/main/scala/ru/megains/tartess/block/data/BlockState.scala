package ru.megains.tartess.block.data


import ru.megains.tartess.block.Block
import ru.megains.tartess.physics.AABB
import ru.megains.tartess.utils.{RayTraceResult, Vec3f}
import ru.megains.tartess.world.World

class BlockState(val block: Block,val pos:BlockPos) {


    def getBoundingBox: AABB = {
        block.getBoundingBox(this)
    }

    def getSelectedBoundingBox: AABB ={
        block.getSelectedBoundingBox(this)
    }

    def collisionRayTrace(world: World, start: Vec3f, end: Vec3f): RayTraceResult = block.collisionRayTrace(world,this, start, end)
}
