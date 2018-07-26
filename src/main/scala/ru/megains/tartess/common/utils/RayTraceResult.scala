package ru.megains.tartess.common.utils

import ru.megains.tartess.common.block.Block
import ru.megains.tartess.common.block.data.{BlockDirection, BlockPos}

class RayTraceResult(val hitVec: Vec3f,val sideHit: BlockDirection,val blockPos: BlockPos,val block: Block){


    def this(hitVecIn: Vec3f, sideHitIn: BlockDirection) {
        this(hitVecIn,sideHitIn,null,null)
    }
}