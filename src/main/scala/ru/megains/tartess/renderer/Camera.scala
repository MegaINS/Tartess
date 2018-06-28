package ru.megains.tartess.renderer

import ru.megains.tartess.utils.Vec3f

class Camera {

    def setRotation(xRot: Float, yRot: Float, i: Int): Unit = {
        rotation.set(xRot,yRot,i)
    }

    def setPosition(posX: Float, posY: Float, posZ: Float): Unit = {
        position.set(posX,posY,posZ)
    }

    val position:Vec3f = new Vec3f()

    val rotation:Vec3f = new Vec3f()
}
