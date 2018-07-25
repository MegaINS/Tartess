package ru.megains.tartess.client.renderer.entity

import java.awt.Color

import org.lwjgl.opengl.GL11._
import ru.megains.tartess.common.entity.Entity
import ru.megains.tartess.common.physics.AABB
import ru.megains.tartess.client.renderer.api.TRenderEntity
import ru.megains.tartess.client.renderer.mesh.{Mesh, MeshMaker}
import ru.megains.tartess.common.world.World


object RenderEntityCube extends TRenderEntity{


    var entityCube:Mesh = _

    override def init(): Unit = {
        val mm = MeshMaker.getMeshMaker
        val aabb:AABB= new AABB(0,0,0,0.6f, 1.8f, 0.6f)

        val minX = aabb.minX
        val minY = aabb.minY
        val minZ = aabb.minZ
        val maxX = aabb.maxX
        val maxY = aabb.maxY
        val maxZ = aabb.maxZ



        mm.startMake(GL_LINES)
        mm.addColor(Color.BLACK)
        mm.addVertex(minX, minY, minZ)
        mm.addVertex(minX, minY, maxZ)
        mm.addVertex(minX, maxY, minZ)
        mm.addVertex(minX, maxY, maxZ)
        mm.addVertex(maxX, minY, minZ)
        mm.addVertex(maxX, minY, maxZ)
        mm.addVertex(maxX, maxY, minZ)
        mm.addVertex(maxX, maxY, maxZ)

        mm.addIndex(0, 1)
        mm.addIndex(0, 2)
        mm.addIndex(0, 4)

        mm.addIndex(6, 2)
        mm.addIndex(6, 4)
        mm.addIndex(6, 7)

        mm.addIndex(3, 1)
        mm.addIndex(3, 2)
        mm.addIndex(3, 7)

        mm.addIndex(5, 1)
        mm.addIndex(5, 4)
        mm.addIndex(5, 7)


        entityCube = mm.makeMesh()
    }

    override def render(entity: Entity, world: World): Boolean = {


        entityCube.render


        true
    }
}
