package ru.megains.tartess.entity

import ru.megains.nbt.NBTType._
import ru.megains.nbt.tag.NBTCompound
import ru.megains.tartess.block.data.BlockDirection
import ru.megains.tartess.physics.{AABB, AABBs}
import ru.megains.tartess.utils.{MathHelper, RayTraceResult, Vec3f}
import ru.megains.tartess.world.World

import scala.collection.mutable
import scala.language.postfixOps

abstract class Entity(val height: Float,val wight: Float,val levelView: Float) {

    var posX:Float =0
    var posY:Float =0
    var posZ:Float =0
    var yRot: Float = 0
    var xRot: Float = 0
    var motionX:Float =0
    var motionY:Float =0
    var motionZ:Float =0
    var goY:Int =8
    var speed: Float = 24
    var onGround:Boolean = false
    val body: AABB = new AABB()
    var world: World = _
    var chunkCoordX = 0
    var chunkCoordY = 0
    var chunkCoordZ = 0
    var side:BlockDirection = BlockDirection.DOWN
    setPosition(0, 16, 0)
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
        var aabbs:mutable.HashSet[AABBs] =  world.addBlocksInList(body.expand(x0, y0, z0))

        aabbs.foreach( aabb=> { y0 = aabb.checkYcollision(body, y0)  } )
        body.move(0, y0, 0)

         aabbs.foreach( aabb=> { x0 = aabb.checkXcollision(body, x0)} )
        body.move(x0, 0, 0)

          aabbs.foreach( aabb=> { z0 = aabb.checkZcollision(body, z0)} )
        body.move(0, 0, z0)

        onGround = y != y0 && y < 0.0F
        var a = true
                if (onGround && (Math.abs(x) > Math.abs(x0) || Math.abs(z) > Math.abs(z0))) {

                    for(i<-0 to goY
                        if a){

                        val bodyCopy1: AABB = bodyCopy.getCopy
                        x1 = x
                        z1 = z
                        y1 = y
                        aabbs =  world.addBlocksInList(bodyCopy1.expand(x1, i, z1))

                        aabbs.foreach( aabb=> { y1 = aabb.checkYcollision(bodyCopy1, i)} )
                        bodyCopy1.move(0, y1, 0)

                        aabbs.foreach( aabb=> { x1 = aabb.checkXcollision(bodyCopy1, x1)} )
                        bodyCopy1.move(x1, 0, 0)

                        aabbs.foreach( aabb=> { z1 = aabb.checkZcollision(bodyCopy1, z1)} )
                        bodyCopy1.move(0, 0, z1)

                        if (Math.abs(x1) > Math.abs(x0) || Math.abs(z1) > Math.abs(z0)) {
                            body.set(bodyCopy1)
                            a = false
                        }
                    }
                }
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


    def readEntityFromNBT(compound: NBTCompound): Unit = {}

    def readFromNBT(compound: NBTCompound) {

        val listPos = compound.getList("Pos")
        val listMotion = compound.getList("Motion")
        val listRotation = compound.getList("Rotation")
        motionX = listMotion.getFloat(0)

        motionY = listMotion.getFloat(1)

        motionZ = listMotion.getFloat(2)

        if (Math.abs(motionX) > 10.0D) motionX = 0.0f
        if (Math.abs(motionY) > 10.0D) motionY = 0.0f
        if (Math.abs(motionZ) > 10.0D) motionZ = 0.0f
        posX = listPos.getFloat(0)

        posY = listPos.getFloat(1)

        posZ = listPos.getFloat(2)

       // prevPosX = posX
       // prevPosY = posY
        //prevPosZ = posZ
        yRot = listRotation.getFloat(0)
        xRot  = listRotation.getFloat(1)
       // rotationYaw = listRotation.getFloat(0)
      //  rotationPitch = listRotation.getFloat(1)
      //  prevRotationYaw = rotationYaw
       // prevRotationPitch = rotationPitch

        onGround = compound.getBoolean("OnGround")
        readEntityFromNBT(compound)
        setPosition(posX, posY, posZ)
        //setRotation(rotationYaw, rotationPitch)

    }

    def writeEntityToNBT(compound: NBTCompound): Unit = {}

    def writeToNBT(compound: NBTCompound): Unit = {

        val listPos = compound.createList("Pos", EnumNBTFloat)
        listPos.setValue(posX)
        listPos.setValue(posY)
        listPos.setValue(posZ)

        val listMotion = compound.createList("Motion",EnumNBTFloat)
        listMotion.setValue(motionX)
        listMotion.setValue(motionY)
        listMotion.setValue(motionZ)

        val listRotation = compound.createList("Rotation", EnumNBTFloat)
       // listRotation.setValue(rotationYaw)
        //listRotation.setValue(rotationPitch)
         listRotation.setValue(yRot)
        listRotation.setValue(xRot)
        compound.setValue("OnGround", onGround)
        writeEntityToNBT(compound)

    }
}
