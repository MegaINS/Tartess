package ru.megains.tartess.entity

import ru.megains.tartess.block.data.BlockDirection
import ru.megains.tartess.physics.AABB
import ru.megains.tartess.utils.{MathHelper, RayTraceResult, Vec3f}
import ru.megains.tartess.world.World

abstract class Entity(val height: Float,val wight: Float,val levelView: Float) {

    var posX:Float =0
    var posY:Float =0
    var posZ:Float =0
    var yRot: Float = 0
    var xRot: Float = 0
    var motionX:Float =0
    var motionY:Float =0
    var motionZ:Float =0
    var goY:Float =0.5f
    var speed: Float = 48
    var onGround:Boolean = false
    val body: AABB = new AABB()
    var world: World = _
    var chunkCoordX = 0
    var chunkCoordY = 0
    var chunkCoordZ = 0
    var side:BlockDirection = BlockDirection.DOWN

    def update(): Unit

    def setPosition(x: Float, y: Float, z: Float) {

        posX = x
        posY = y
        posZ = z
        val i = wight/2
        body.set(x-i, y, z-i, x+i,y+ height, z+i)
    }

    def move(x: Float, y: Float, z: Float) {
        var x0: Float = x
        var z0: Float = z
        var y0: Float = y
        var x1: Float = x
        var z1: Float = z
        var y1: Float = y

        val bodyCopy: AABB = body.getCopy
        // var aabbs:mutable.ArrayBuffer[AxisAlignedBB] =  world.addBlocksInList(body.expand(x0, y0, z0))

        // aabbs.foreach( (aabb:AxisAlignedBB)=> { y0 = aabb.checkYcollision(body, y0)} )
        body.move(0, y0, 0)

        // aabbs.foreach( (aabb:AxisAlignedBB)=> { x0 = aabb.checkXcollision(body, x0)} )
        body.move(x0, 0, 0)

        //  aabbs.foreach( (aabb:AxisAlignedBB)=> { z0 = aabb.checkZcollision(body, z0)} )
        body.move(0, 0, z0)

        onGround = y != y0 && y < 0.0F
        //        if (onGround && (Math.abs(x) > Math.abs(x0) || Math.abs(z) > Math.abs(z0))) {
        //
        //            aabbs =  world.addBlocksInList(bodyCopy.expand(x1, goY, z1))
        //
        //            aabbs.foreach( (aabb:AxisAlignedBB)=> { y1 = aabb.checkYcollision(body, goY)} )
        //            body.move(0, y1, 0)
        //
        //            aabbs.foreach( (aabb:AxisAlignedBB)=> { x1 = aabb.checkXcollision(body, x1)} )
        //            body.move(x1, 0, 0)
        //
        //            aabbs.foreach( (aabb:AxisAlignedBB)=> { z1 = aabb.checkZcollision(body, z1)} )
        //            body.move(0, 0, z1)
        //
        //            if (Math.abs(x1) > Math.abs(x0) || Math.abs(z1) > Math.abs(z0)) {
        //                body.set(bodyCopy)
        //            }
        //        }
        if (x0 != x & x1 != x) {
            motionX = 0.0F
        }
        if (y0 != y) {
            motionY = 0.0F
        }
        if (z0 != z & z1 != z) {
            motionZ = 0.0F
        }

        posX = body.getCenterX
        posY = body.minY
        posZ = body.getCenterZ

    }

    def moveFlying( x: Float, z: Float, limit: Float) {
        var dist: Float = x * x + z * z
        if (dist >= 1.0E-4F) {
            dist = MathHelper.sqrt_float(dist)
            if (dist < 1.0F) {
                dist = 1.0F
            }
            dist = limit / dist * speed
            val x1 = dist * x
            val z1 = dist * z
            val f4: Float = MathHelper.sin(yRot * Math.PI.toFloat / 180.0F)
            val f5: Float = MathHelper.cos(yRot * Math.PI.toFloat / 180.0F)
            motionX += (x1 * f5 - z1 * f4)
            motionZ += (z1 * f5 + x1 * f4)
        }
    }

    def rayTrace(blockReachDistance: Float, partialTicks: Float): RayTraceResult = {

        val vec3d = new Vec3f(posX,posY+levelView,posZ)
        val vec3d1: Vec3f = getLook(partialTicks).mul(blockReachDistance).add(vec3d)
        world.rayTraceBlocks(vec3d, vec3d1, stopOnLiquid = false, ignoreBlockWithoutBoundingBox = false, returnLastUncollidableBlock = true)
    }

    def getLook(partialTicks: Float): Vec3f = {
        if (partialTicks == 1.0F) {
            getVectorForRotation(this.xRot, this.yRot)
        }
        else {
            val f: Float = xRot
            val f1: Float = yRot
            getVectorForRotation(f, f1)
        }
    }

    def getVectorForRotation(pitch : Float, yaw : Float):Vec3f = {
        val f : Float = MathHelper.cos(-yaw * 0.017453292F - Math.PI.toFloat)
        val f1 : Float = MathHelper.sin(-yaw * 0.017453292F - Math.PI.toFloat)
        val f2 : Float = MathHelper.cos(-pitch * 0.017453292F)
        val f3 : Float = MathHelper.sin(-pitch * 0.017453292F)
        new Vec3f(f1 * f2, f3, f * f2)
    }
}
