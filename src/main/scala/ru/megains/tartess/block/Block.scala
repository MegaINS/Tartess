package ru.megains.tartess.block

import ru.megains.old.util.RayTraceResult
import ru.megains.old.utils.Vec3f
import ru.megains.tartess.block.data.{BlockDirection, BlockPos, BlockState}
import ru.megains.tartess.physics.AABB
import ru.megains.tartess.renderer.texture.{TTextureRegister, TextureAtlas}
import ru.megains.tartess.world.World

class Block(val name:String) {

    var texture: TextureAtlas = _
    val blockBody:AABB = Block.FULL_AABB


    def registerTexture(textureRegister: TTextureRegister): Unit = {
        texture = textureRegister.registerTexture(name)
    }

    def getATexture(pos: BlockPos = null,blockDirection: BlockDirection = BlockDirection.UP,world: World = null): TextureAtlas = texture

    def getSelectedBoundingBox(blockState: BlockState): AABB = blockBody//.rotate(blockState.blockDirection)

    def getBoundingBox(blockState: BlockState): AABB = getSelectedBoundingBox(blockState).sum(blockState.pos.x, blockState.pos.y, blockState.pos.z)

    def collisionRayTrace(world: World, blockState: BlockState, start: Vec3f, end: Vec3f): RayTraceResult = {
        val pos = blockState.pos
        val vec3d: Vec3f = new Vec3f(start.x toFloat, start.y toFloat, start.z toFloat).sub(pos.x, pos.y, pos.z)
        val vec3d1: Vec3f = new Vec3f(end.x toFloat, end.y toFloat, end.z toFloat).sub(pos.x, pos.y, pos.z)
        val rayTraceResult = getSelectedBoundingBox(blockState).calculateIntercept(vec3d, vec3d1)

        if (rayTraceResult == null) {
            null
        } else {
            new RayTraceResult(rayTraceResult.hitVec.add(pos.x, pos.y, pos.z), rayTraceResult.sideHit, pos, this)
        }
    }
}

object Block{

    val FULL_AABB = new AABB(0,0,0,1,1,1)
    val NULL_AABB = new AABB(0,0,0,0,0,0)

}