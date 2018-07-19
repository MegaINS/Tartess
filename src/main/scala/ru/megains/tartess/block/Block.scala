package ru.megains.tartess.block


import ru.megains.tartess.block.data.{BlockDirection, BlockPos, BlockState}
import ru.megains.tartess.entity.Entity
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.item.itemstack.ItemPack
import ru.megains.tartess.physics.{AABB, BoundingBox}
import ru.megains.tartess.renderer.texture.{TTextureRegister, TextureAtlas}
import ru.megains.tartess.utils.{RayTraceResult, Vec3f}
import ru.megains.tartess.world.World

import scala.language.postfixOps

abstract class Block(val name:String) {


    val mass = 1
    var texture: TextureAtlas = _
    val blockBody:AABB
    val boundingBox:BoundingBox
    val airState = new BlockState(this,new BlockPos(0,0,0))

    def isOpaqueCube = true

    def registerTexture(textureRegister: TTextureRegister): Unit = {
        texture = textureRegister.registerTexture(name)
    }

    def getATexture(blockState: BlockState,blockDirection: BlockDirection,world: World ): TextureAtlas = texture

    def getATexture(pos: BlockPos = null,blockDirection: BlockDirection = BlockDirection.UP,world: World = null): TextureAtlas = texture

    def getSelectedBoundingBox(blockState: BlockState): Array[BoundingBox]  = getBoundingBox(blockState).map(_.sum(blockState.pos.x, blockState.pos.y, blockState.pos.z))

    def getBoundingBox(blockState: BlockState): Array[BoundingBox] =  Array(boundingBox.rotate(blockState.blockDirection))

    def getBlockBody(state: BlockState):  Array[AABB] = Array(blockBody.rotate(state.blockDirection))

    def getSelectedBlockBody(blockState: BlockState):  Array[AABB]= getBlockBody(blockState).map(_.sum(blockState.pos.x, blockState.pos.y, blockState.pos.z))

    def collisionRayTrace(world: World, blockState: BlockState, start: Vec3f, end: Vec3f): RayTraceResult = {
        val pos = blockState.pos
        val vec3d: Vec3f = new Vec3f(start.x , start.y , start.z ).sub(pos.x, pos.y, pos.z)
        val vec3d1: Vec3f = new Vec3f(end.x , end.y , end.z ).sub(pos.x, pos.y, pos.z)
        var res:RayTraceResult = null
        val blockBodys = getBlockBody(blockState)
        for(blockBody<-blockBodys){
            val rayTraceResult = blockBody.calculateIntercept( vec3d, vec3d1 )

            if (rayTraceResult == null) {
                null
            } else {
                res =  new RayTraceResult(rayTraceResult.hitVec.add(pos.x, pos.y, pos.z), rayTraceResult.sideHit, pos, this)
            }
        }

        res


//        val rayTraceResult = getBlockBody(blockState).calculateIntercept( vec3d, vec3d1 )
//
//        if (rayTraceResult == null) {
//            null
//        } else {
//            new RayTraceResult(rayTraceResult.hitVec.add(pos.x, pos.y, pos.z), rayTraceResult.sideHit, pos, this)
//        }
    }

    def onBlockActivated(world: World, pos: BlockPos, player: EntityPlayer, itemStack: ItemPack, blockDirection:BlockDirection, float1: Float, float2: Float): Boolean = {
        false
    }

    def getSelectPosition(worldIn: World,entity: Entity, objectMouseOver: RayTraceResult): BlockState = {
        //TODO
        val side = entity.side
        val posTarget: BlockPos = objectMouseOver.blockPos
        val hitVec: Vec3f = objectMouseOver.hitVec
        var posSet: BlockPos = objectMouseOver.sideHit match {
            case BlockDirection.DOWN => posTarget.sum(0,-boundingBox.maxY ,0)
            case BlockDirection.WEST => posTarget.sum(-boundingBox.maxX,0,0)
            case BlockDirection.NORTH => posTarget.sum(0,0,-boundingBox.maxZ)
            case _ => posTarget
        }
        posSet =  posSet.sum(hitVec.x  - posTarget.x toInt,hitVec.y - posTarget.y toInt,hitVec.z - posTarget.z toInt)

        posSet = side match {
            case BlockDirection.EAST  => posSet.sum(0,0,Math.floor(-boundingBox.rotate(BlockDirection.EAST).maxZ/2f) +1  toInt)
            case BlockDirection.WEST => posSet.sum(-boundingBox.rotate(BlockDirection.WEST).maxX+1,0,-boundingBox.rotate(BlockDirection.WEST).maxZ/2f toInt)
            case BlockDirection.SOUTH => posSet.sum(-boundingBox.rotate(BlockDirection.SOUTH).maxX /2,0,0)
            case BlockDirection.NORTH => posSet.sum(Math.floor(-boundingBox.rotate(BlockDirection.NORTH).maxX/2f) +1  toInt,0,-boundingBox.rotate(BlockDirection.NORTH).maxZ+1)
            case _ => posSet
        }


        val blockState = new BlockState(this,posSet,side)
        if (worldIn.isAirBlock(blockState)) blockState
        else null
    }
}

