package ru.megains.tartess.common.entity.mob

import ru.megains.tartess.common.entity.EntityLivingBase

import scala.util.Random


class EntityCube() extends EntityLivingBase(1.8f*16, 0.6f*16, 1.6f*16) {


    rotationYaw = (Random.nextFloat()-0.5f)*720f




    var x = 0
    var t = 5

    override def update(): Unit = {

        var xo = 0
        var zo = 0
        x+=1
        if(x % t == 0){
            t = Random.nextInt(20)+1
            xo = if(Random.nextFloat()>0.1) 1 else 0
            zo = if(Random.nextFloat()>0.1) 1 else 0
            if(onGround){
                motionY = if( Random.nextFloat()<0.2) 0.42f * 16 else 0
            }

            if(x % 100 == 0){
                turn(0,(Random.nextFloat()-0.5f)*720f)
            }
        }

        moveFlying(xo, zo, if (onGround) 0.04f else 0.02f)
        move(motionX, motionY, motionZ)
        motionX *= 0.8f
        if (motionY > 0.0f) {
            motionY *= 0.90f
        }
        else {
            motionY *= 0.98f
        }
        motionZ *= 0.8f
        motionY -= 0.04f*16
        if (onGround) {
            motionX *= 0.9f
            motionZ *= 0.9f
        }
    }

    def turn(xo: Float, yo: Float) {
        rotationYaw += yo //* 0.15f
        rotationPitch += xo //* 0.15f
        if (rotationPitch < -90.0F) {
            rotationPitch = -90.0F
        }
        if (rotationPitch > 90.0F) {
            rotationPitch = 90.0F
        }
        if (rotationYaw > 360.0F) {
            rotationYaw -= 360.0F
        }
        if (rotationYaw < 0.0F) {
            rotationYaw += 360.0F
        }
    }

//    override def getItemStackFromSlot: ItemStack = {
//        null
//    }
//
//    override def setItemStackToSlot(stack: ItemStack): Unit = {
//
//    }
}
