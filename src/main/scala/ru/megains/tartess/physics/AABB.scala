package ru.megains.tartess.physics

import ru.megains.old.util.RayTraceResult
import ru.megains.old.utils.Vec3f
import ru.megains.tartess.block.data.BlockDirection

class AABB( var minX:Double = .0,
            var minY:Double = .0,
            var minZ:Double = .0,
            var maxX:Double = .0,
            var maxY:Double = .0,
            var maxZ:Double = .0) {


    def sum(x: Float, y: Float, z: Float) = new AABB(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z)

    def calculateIntercept(vecA: Vec3f, vecB: Vec3f): RayTraceResult = {
        var vec3d = this.func_186671_a(this.minX, vecA, vecB)
        var enumfacing:BlockDirection = BlockDirection.WEST
        var vec3d1 = this.func_186671_a(this.maxX, vecA, vecB)
        if (vec3d1 != null && this.func_186661_a(vecA, vec3d, vec3d1)) {
            vec3d = vec3d1
            enumfacing = BlockDirection.EAST
        }
        vec3d1 = this.func_186663_b(this.minY, vecA, vecB)
        if (vec3d1 != null && this.func_186661_a(vecA, vec3d, vec3d1)) {
            vec3d = vec3d1
            enumfacing = BlockDirection.DOWN
        }
        vec3d1 = this.func_186663_b(this.maxY, vecA, vecB)
        if (vec3d1 != null && this.func_186661_a(vecA, vec3d, vec3d1)) {
            vec3d = vec3d1
            enumfacing = BlockDirection.UP
        }
        vec3d1 = this.func_186665_c(this.minZ, vecA, vecB)
        if (vec3d1 != null && this.func_186661_a(vecA, vec3d, vec3d1)) {
            vec3d = vec3d1
            enumfacing = BlockDirection.NORTH
        }
        vec3d1 = this.func_186665_c(this.maxZ, vecA, vecB)
        if (vec3d1 != null && this.func_186661_a(vecA, vec3d, vec3d1)) {
            vec3d = vec3d1
            enumfacing = BlockDirection.SOUTH
        }
        if (vec3d == null) null
        else new RayTraceResult(vec3d, enumfacing)
    }

    private[physics] def func_186661_a(p_186661_1:Vec3f, p_186661_2: Vec3f, p_186661_3: Vec3f): Boolean = {
        return p_186661_2 == null || p_186661_1.distanceSquared(p_186661_3) < p_186661_1.distanceSquared(p_186661_2)
    }

    private[physics] def func_186671_a(p_186671_1:Double, p_186671_3: Vec3f, p_186671_4: Vec3f): Vec3f = {
        val vec3d = p_186671_3.getIntermediateWithXValue(p_186671_4, p_186671_1)
        return if (vec3d != null && this.intersectsWithYZ(vec3d)) vec3d
        else null
    }

    private[physics] def func_186663_b(p_186663_1:Double, p_186663_3: Vec3f, p_186663_4: Vec3f): Vec3f = {
        val vec3d = p_186663_3.getIntermediateWithYValue(p_186663_4, p_186663_1)
        return if (vec3d != null && this.intersectsWithXZ(vec3d)) vec3d
        else null
    }

    private[physics] def func_186665_c(p_186665_1:Double, p_186665_3: Vec3f, p_186665_4: Vec3f): Vec3f = {
        val vec3d = p_186665_3.getIntermediateWithZValue(p_186665_4, p_186665_1)
        return if (vec3d != null && this.intersectsWithXY(vec3d)) vec3d
        else null
    }

    def intersectsWithYZ(vec: Vec3f): Boolean = vec.y >= this.minY && vec.y <= this.maxY && vec.z >= this.minZ && vec.z <= this.maxZ

    def intersectsWithXZ(vec: Vec3f): Boolean = vec.x >= this.minX && vec.x <= this.maxX && vec.z >= this.minZ && vec.z <= this.maxZ

    def intersectsWithXY(vec: Vec3f): Boolean = vec.x >= this.minX && vec.x <= this.maxX && vec.y >= this.minY && vec.y <= this.maxY
}