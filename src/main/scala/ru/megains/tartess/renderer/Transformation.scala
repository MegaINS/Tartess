package ru.megains.tartess.renderer

import org.joml.{Matrix4f, Vector3f}
import ru.megains.tartess.block.data.BlockPos
import ru.megains.tartess.world.chunk.ChunkPosition

class Transformation {

    val projectionMatrix = new Matrix4f
    val viewMatrix = new Matrix4f
    val modelViewMatrix = new Matrix4f
    val ortho2DMatrix = new Matrix4f
    val modelMatrix = new Matrix4f

    def updateProjectionMatrix(fov: Float, width: Float, height: Float, zNear: Float, zFar: Float, camera: Camera): Matrix4f = {
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
        modelViewMatrix.translate(position.minXR, position.minYR, position.minZR)
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

    def buildBlockModelViewMatrix(blockPos: BlockPos): Matrix4f = {
        modelViewMatrix.identity
        modelViewMatrix.translate(blockPos.x , blockPos.y , blockPos.z )

    }
}