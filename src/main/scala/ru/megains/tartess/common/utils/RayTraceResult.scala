package ru.megains.tartess.common.utils

import ru.megains.tartess.common.block.Block
import ru.megains.tartess.common.block.data.{BlockDirection, BlockPos}
import ru.megains.tartess.common.utils.RayTraceType.RayTraceType

class RayTraceResult(val rayTraceType: RayTraceType, val hitVec: Vec3f,val sideHit: BlockDirection,val blockPos: BlockPos,val block: Block){
       def this(){
           this(RayTraceType.VOID,null,null,null,null)
       }
}


