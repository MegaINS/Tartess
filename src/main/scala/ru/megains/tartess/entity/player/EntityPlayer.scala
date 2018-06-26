package ru.megains.tartess.entity.player

import ru.megains.tartess.entity.EntityLivingBase
import ru.megains.tartess.inventory.InventoryPlayer

class EntityPlayer extends EntityLivingBase(1.8f*16, 0.6f*16, 1.6f*16) {


    val inventory = new InventoryPlayer(this)

    def turn(xo: Float, yo: Float) {
        yRot += yo * 0.15f
        xRot += xo * 0.15f
        if (xRot < -90.0F) {
            xRot = -90.0F
        }
        if (xRot > 90.0F) {
            xRot = 90.0F
        }
        if (yRot > 360.0F) {
            yRot -= 360.0F
        }
        if (yRot < 0.0F) {
            yRot += 360.0F
        }
    }

    def update(xo: Float, yo: Float, zo: Float) {

        if (yo > 0) {
        }

        motionY = yo / 2
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
        motionY -= 0.04f
        if (onGround) {
            motionX *= 0.9f
            motionZ *= 0.9f
        }
    }

    override def update(): Unit = ???
}
