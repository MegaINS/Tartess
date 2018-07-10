package ru.megains.tartess.renderer

import org.joml.{Matrix4f, Vector3f}
import ru.megains.tartess.block.data.BlockPos
import ru.megains.tartess.entity.Entity
import ru.megains.tartess.entity.item.EntityItem
import ru.megains.tartess.physics.AABB
import ru.megains.tartess.world.chunk.data.ChunkPosition

import scala.language.postfixOps

class Transformation {

    val projectionMatrix = new Matrix4f
    val viewMatrix = new Matrix4f
    val modelViewMatrix = new Matrix4f
    val ortho2DMatrix = new Matrix4f
    val modelMatrix = new Matrix4f

    def updateProjectionMatrix(fov: Float, width: Float, height: Float, zNear: Float, zFar: Float): Matrix4f = {
        val aspectRatio = width / height
        projectionMatrix.identity
        projectionMatrix.perspective(fov, aspectRatio, zNear, zFar)
    }

    private def updateGenericViewMatrix(position: Vector3f, rotation: Vector3f) = {
        viewMatrix.identity
        viewMatrix.rotate(Math.toRadians(rotation.x).toFloat, new Vector3f(1, 0, 0)).rotate(Math.toRadians(rotation.y).toFloat, new Vector3f(0, 1, 0))
        viewMatrix.translate(-position.x, -position.y, -position.z)
    }

    def updateViewMatrix(camera: Camera): Matrix4f = updateGenericViewMatrix(camera.position, camera.rotation)

    def buildChunkModelViewMatrix(position: ChunkPosition): Matrix4f = {
        modelViewMatrix.identity
        modelViewMatrix.translate(position.x * 16, position.y * 16, position.z * 16)
    }

    def getOrtho2DProjectionMatrix(left: Float, right: Float, bottom: Float, top: Float): Matrix4f = {
        ortho2DMatrix.identity
        ortho2DMatrix.setOrtho(left, right, bottom, top, -100, 2000)
    }

    def buildOrtoProjModelMatrix(x: Int, y: Int, scale: Float): Matrix4f = {
        modelMatrix.identity
        modelMatrix.translate(x, y, 0)
        modelMatrix.scale(scale)
    }

    def buildOrtoProjModelMatrix(xPos: Int, yPos: Int, zPos: Int, xPot: Int, yPot: Int, zPot: Int, scale: Float): Matrix4f ={
        modelMatrix.identity
        modelMatrix.translate(xPos, yPos, zPos)
        modelMatrix.rotateXYZ(Math.toRadians(-xPot).toFloat, Math.toRadians(-yPot).toFloat, Math.toRadians(-zPot).toFloat)
        modelMatrix.scale(scale)
    }

    def buildObjectMouseOverViewMatrix(blockPos: BlockPos): Matrix4f = {
        modelViewMatrix.identity
        modelViewMatrix.translate(blockPos.x /16f, blockPos.y /16f, blockPos.z /16f)
    }

    def buildEntityItemModelViewMatrix(entity: EntityItem): Matrix4f = {

        val position:AABB = entity.body
        val anim2 = (Math.sin(System.currentTimeMillis() % 2000.0 / 2000 * 2 * Math.PI) * 0.1f) + 0.4f
        modelMatrix.identity
        modelMatrix.translate((position.minX + 0.125f) /16 , position.minY / 16 + anim2 toFloat, (position.minZ + 0.125f)/16)
        modelMatrix.rotateY( ((entity.posX  toInt) << 2 & 360) +  Math.toRadians( System.currentTimeMillis() % 10801.0f / 30f) toFloat)
        modelMatrix.scale(0.25f)
    }

    def buildEntityModelViewMatrix(entity: Entity): Matrix4f = {
        val position:AABB = entity.body
        modelMatrix.identity
        modelMatrix.translate(position.minX /16 , position.minY / 16  , position.minZ /16)
    }

}
