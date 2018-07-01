package ru.megains.tartess.block


import ru.megains.tartess.block.data.{BlockDirection, BlockPos, BlockState}
import ru.megains.tartess.entity.Entity
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.item.itemstack.ItemStack
import ru.megains.tartess.physics.AABB
import ru.megains.tartess.renderer.texture.{TTextureRegister, TextureAtlas}
import ru.megains.tartess.utils.{RayTraceResult, Vec3f}
import ru.megains.tartess.world.World

import scala.language.postfixOps

class Block(val name:String) {

    var texture: TextureAtlas = _
    val blockBody:AABB = Block.FULL_AABB
    val blockState = new BlockState(this,null)

    def isOpaqueCube = true

    def registerTexture(textureRegister: TTextureRegister): Unit = {
        texture = textureRegister.registerTexture(name)
    }

    def getATexture(pos: BlockPos = null,blockDirection: BlockDirection = BlockDirection.UP,world: World = null): TextureAtlas = texture

    def getSelectedBoundingBox(blockState: BlockState): AABB = blockBody//.rotate(blockState.blockDirection)

    def getBoundingBox(blockState: BlockState): AABB = getSelectedBoundingBox(blockState).sum(blockState.pos.x, blockState.pos.y, blockState.pos.z)

    def collisionRayTrace(world: World, blockState: BlockState, start: Vec3f, end: Vec3f): RayTraceResult = {
        val pos = blockState.pos
        val vec3d: Vec3f = new Vec3f(start.x , start.y , start.z ).sub(pos.x, pos.y, pos.z)
        val vec3d1: Vec3f = new Vec3f(end.x , end.y , end.z ).sub(pos.x, pos.y, pos.z)
        val rayTraceResult = getSelectedBoundingBox(blockState).calculateIntercept(vec3d, vec3d1)

        if (rayTraceResult == null) {
            null
        } else {
            new RayTraceResult(rayTraceResult.hitVec.add(pos.x, pos.y, pos.z), rayTraceResult.sideHit, pos, this)
        }
    }

    def onBlockActivated(world: World, pos: BlockPos, player: EntityPlayer, itemStack: ItemStack, blockDirection:BlockDirection, float1: Float, float2: Float): Boolean = {
        false
    }

    def getSelectPosition(worldIn: World,entity: Entity, objectMouseOver: RayTraceResult): BlockState = {
        val side = entity.side
        //Todo доделать
        val blockBodyTarget = objectMouseOver.block.blockBody
        val posTarget: BlockPos = objectMouseOver.blockPos
        var posSet: BlockPos = objectMouseOver.sideHit match {
            case BlockDirection.DOWN => posTarget.sum(0,-blockBody.maxY toInt,0)
            case BlockDirection.WEST => posTarget.sum(-blockBody.maxX toInt,0,0)
            case BlockDirection.NORTH => posTarget.sum(0,0,-blockBody.maxZ toInt)
            case BlockDirection.UP => posTarget.sum(0,blockBodyTarget.maxY toInt,0)
            case BlockDirection.EAST => posTarget.sum(blockBodyTarget.maxX toInt,0,0)
            case BlockDirection.SOUTH => posTarget.sum(0,0,blockBodyTarget.maxZ toInt)
            case _ => posTarget
        }
//
//        posSet = side match {
//            case BlockDirection.EAST  => posTarget.sum(0,0,Math.floor(-blockSize.width/2f)  + 1 toInt)
//            case BlockDirection.WEST => posTarget.sum(-blockSize.length+1,0,-blockSize.width/2 )
//            case BlockDirection.SOUTH => posTarget.sum(-blockSize.width/2,0,0)
//            case BlockDirection.NORTH => posTarget.sum(Math.floor(-blockSize.width/2f)  + 1 toInt,0,-blockSize.length+1)
//            case _ => posTarget.sum(0,0,0)
//        }
//
//
//
//
        val blockState = new BlockState(this,posSet)
        if (worldIn.isAirBlock(blockState)) blockState
        else null
    }
}

object Block{

    val FULL_AABB = new AABB(0,0,0,16,16,16)
    val NULL_AABB = new AABB(0,0,0,0,0,0)

}
