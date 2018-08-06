package ru.megains.tartess.common.block


import ru.megains.tartess.common.block.data.{BlockDirection, BlockPos, BlockState}
import ru.megains.tartess.common.entity.Entity
import ru.megains.tartess.common.entity.player.EntityPlayer
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.common.physics.{AABBs, BoundingBoxes}
import ru.megains.tartess.common.utils.{RayTraceResult, RayTraceType, Vec3f}
import ru.megains.tartess.common.world.World

import scala.language.postfixOps

abstract class Block(val name:String) {


    val mass = 1
    val blockBodies:AABBs
    val boundingBoxes:BoundingBoxes
    val airState = new BlockState(this,new BlockPos(0,0,0))

    def isOpaqueCube = true


    def getSelectedBoundingBox(blockState: BlockState): BoundingBoxes  = getBoundingBox(blockState).sum(blockState.pos.x, blockState.pos.y, blockState.pos.z)

    def getBoundingBox(blockState: BlockState):BoundingBoxes =  boundingBoxes.rotate(blockState.blockDirection)

    def getBlockBody(state: BlockState):  AABBs = blockBodies.rotate(state.blockDirection)

    def getSelectedBlockBody(blockState: BlockState):  AABBs= getBlockBody(blockState).sum(blockState.pos.x, blockState.pos.y, blockState.pos.z)

    def collisionRayTrace(world: World, blockState: BlockState, start: Vec3f, end: Vec3f): RayTraceResult = {
        val pos = blockState.pos
        val vec3d: Vec3f = new Vec3f(start.x , start.y , start.z ).sub(pos.x, pos.y, pos.z)
        val vec3d1: Vec3f = new Vec3f(end.x , end.y , end.z ).sub(pos.x, pos.y, pos.z)
        val res = getBlockBody(blockState).calculateIntercept(vec3d, vec3d1 )

        if (res != null) new RayTraceResult(RayTraceType.BLOCK, res._1.add(pos.x, pos.y, pos.z), res._2, pos, this)
        else new RayTraceResult()
    }

    def onBlockActivated(world: World, pos: BlockPos, player: EntityPlayer, itemStack: ItemPack, blockDirection:BlockDirection, float1: Float, float2: Float,float3: Float): Boolean = {
        false
    }

    def getSelectPosition(worldIn: World,entity: Entity, objectMouseOver: RayTraceResult): BlockState = {

        val side = entity.side
        val posTarget: BlockPos = objectMouseOver.blockPos
        val hitVec: Vec3f = objectMouseOver.hitVec
        var posSet: BlockPos = objectMouseOver.sideHit match {
            case BlockDirection.DOWN => posTarget.sum(0,-boundingBoxes.maxY ,0)
            case BlockDirection.WEST => posTarget.sum(-boundingBoxes.rotate(side).maxX,0,0)
            case BlockDirection.NORTH => posTarget.sum(0,0,-boundingBoxes.rotate(side).maxZ)
            case _ => posTarget
        }
       posSet =  posSet.sum(hitVec.x  - posTarget.x toInt,hitVec.y - posTarget.y toInt,hitVec.z - posTarget.z toInt)



        posSet = objectMouseOver.sideHit match {
            case BlockDirection.EAST =>
                side match {
                    case BlockDirection.WEST => posSet.sum(0,0,-boundingBoxes.maxZ/2f toInt)
                    case BlockDirection.NORTH => posSet.sum(0,0,-boundingBoxes.rotate(BlockDirection.NORTH).maxZ+1)
                    case _ => posSet
                }
             case BlockDirection.WEST =>
                 side match {
                     case BlockDirection.EAST => posSet.sum(0,0,-boundingBoxes.maxZ/2f+1 toInt)
                     case BlockDirection.NORTH => posSet.sum(0,0,-boundingBoxes.rotate(BlockDirection.NORTH).maxZ+1)
                     case _ => posSet
                 }
            case BlockDirection.SOUTH =>
                side match {
                    case BlockDirection.WEST => posSet.sum(-boundingBoxes.rotate(BlockDirection.WEST).maxX+1,0,0)
                    case BlockDirection.NORTH => posSet.sum(-boundingBoxes.rotate(BlockDirection.NORTH).maxX/2,0,0)
                    case _ => posSet
                }

            case BlockDirection.NORTH =>
                side match {
                    case BlockDirection.WEST => posSet.sum(-boundingBoxes.rotate(BlockDirection.WEST).maxX+1,0,0)
                    case BlockDirection.SOUTH => posSet.sum(-boundingBoxes.rotate(BlockDirection.SOUTH).maxX/2,0,0)
                    case _ => posSet
                }
            case BlockDirection.UP | BlockDirection.DOWN  =>
                side match {
                    case BlockDirection.EAST  => posSet.sum(0,0,Math.floor(-boundingBoxes.rotate(BlockDirection.EAST).maxZ/2f) +1  toInt)
                    case BlockDirection.WEST => posSet.sum(-boundingBoxes.rotate(BlockDirection.WEST).maxX+1,0,-boundingBoxes.rotate(BlockDirection.WEST).maxZ/2f toInt)
                    case BlockDirection.SOUTH => posSet.sum(-boundingBoxes.rotate(BlockDirection.SOUTH).maxX /2,0,0)
                    case BlockDirection.NORTH => posSet.sum(Math.floor(-boundingBoxes.rotate(BlockDirection.NORTH).maxX/2f) +1  toInt,0,-boundingBoxes.rotate(BlockDirection.NORTH).maxZ+1)
                    case _ => posSet
                }
            case _ => posSet
        }




        val blockState = new BlockState(this,posSet,side)
        if (worldIn.isAirBlock(blockState)) blockState else null
    }

    def removedByPlayer(world: World, pos: BlockPos, player: EntityPlayer, willHarvest: Boolean): Boolean = {
        onBlockHarvested(world, pos, player)
        world.setAirBlock(pos)
    }

    def onBlockHarvested(worldIn: World, pos: BlockPos, player: EntityPlayer) {
    }

    def onBlockDestroyedByPlayer(worldIn: World, pos: BlockPos) {
    }
}

