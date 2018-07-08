package ru.megains.tartess.entity.mob

import ru.megains.tartess.entity.EntityLivingBase


class EntityCube() extends EntityLivingBase(1.8f*16, 0.6f*16, 1.6f*16) {


    override def update(): Unit = {


        moveFlying(0, 0, if (onGround) 0.04f else 0.02f)

        move(motionX, motionY, motionZ)

        motionX *= 0.8f
        motionY *= {
            if (motionY > 0.0) 0.90f else 0.98f
        }
        motionZ *= 0.8f
        motionY -= 0.04f
        if (onGround) {
            motionX *= 0.9f
            motionZ *= 0.9f
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
