package ru.megains.tartess.renderer

import org.joml.Vector3f

class Camera {
    def setRotation(xRot: Float, yRot: Float, i: Int): Unit = {
        rotation.set(xRot,yRot,i)
    }


    def setPosition(posX: Float, posY: Float, posZ: Float): Unit = {
        position.set(posX,posY,posZ)
    }

    val position:Vector3f = new Vector3f()

    val rotation:Vector3f = new Vector3f()
}
