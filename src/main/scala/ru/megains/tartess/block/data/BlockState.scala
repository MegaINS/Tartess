package ru.megains.tartess.block.data


import ru.megains.tartess.block.Block
import ru.megains.tartess.physics.{AABB, BoundingBox}
import ru.megains.tartess.renderer.texture.TextureAtlas
import ru.megains.tartess.utils.{RayTraceResult, Vec3f}
import ru.megains.tartess.world.World

class BlockState(val block: Block,val pos:BlockPos,val blockDirection:BlockDirection = BlockDirection.NONE) {

    def getTTexture(blockDirection: BlockDirection,world: World ): TextureAtlas = block.getATexture(this,blockDirection: BlockDirection ,world)

    def getBlockBody:Array[AABB]  = block.getBlockBody(this)

    def getSelectedBlockBody:Array[AABB] = block.getSelectedBlockBody(this)

    def getBoundingBox:Array[BoundingBox]  = block.getBoundingBox(this)

    def getSelectedBoundingBox:Array[BoundingBox]  = block.getSelectedBoundingBox(this)

    def collisionRayTrace(world: World, start: Vec3f, end: Vec3f): RayTraceResult = block.collisionRayTrace(world,this, start, end)
}
