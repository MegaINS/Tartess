package ru.megains.tech.block

import ru.megains.tartess.block.data.BlockDirection
import ru.megains.tech.block.blockdata.{BlockPos, BlockSidePos}
import ru.megains.tech.physics.AxisAlignedBB

class BlockState(val block: Block,val pos: BlockPos,val blockDirection: BlockDirection = BlockDirection.NONE) {





    def getSelectedBoundingBox: AxisAlignedBB = block.getSelectedBoundingBox(this)


   // def collisionRayTrace(world: World, start: Vector3d, end: Vector3d): RayTraceResult = block.collisionRayTrace(world,this, start, end)

    def getBoundingBox: AxisAlignedBB = {
        block.getBoundingBox(this)
    }

    def getSidePos(side: BlockDirection):BlockSidePos ={
        block.getSidePos(this,side)
    }

}
