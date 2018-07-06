package ru.megains.tartess.physics


import ru.megains.tartess.block.data.BlockDirection
import ru.megains.tartess.utils.{RayTraceResult, Vec3f}

class AABB( var minX:Float = .0f,
            var minY:Float = .0f,
            var minZ:Float = .0f,
            var maxX:Float = .0f,
            var maxY:Float = .0f,
            var maxZ:Float = .0f) {

    def this(size:Float){
        this(0,0,0,size,size,size)
    }


    def rotate(side: BlockDirection): AABB = {
        side match {
            case BlockDirection.EAST | BlockDirection.WEST=> new AABB(minZ, minY, minX, maxZ, maxY, maxX)
            case _  => getCopy
        }
    }


    def set(minX: Float, minY: Float, minZ: Float, maxX: Float, maxY: Float, maxZ: Float): Unit = {
        this.minX = minX
        this.minY = minY
        this.minZ = minZ
        this.maxX = maxX
        this.maxY = maxY
        this.maxZ = maxZ
    }

    def sum(x: Float, y: Float, z: Float) = new AABB(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z)

    //def div(value: Float) = new AABB(minX / value, minY / value, minZ / value, maxX / value, maxY / value, maxZ / value)

    def getCenterX: Float = (maxX + minX) / 2

    def getCenterZ: Float = (maxZ + minZ) / 2

    def getCopy = new AABB(minX, minY, minZ, maxX, maxY, maxZ)

    def move(x: Float, y: Float, z: Float): AABB = {
        minX += x
        minY += y
        minZ += z
        maxX += x
        maxY += y
        maxZ += z
        this
    }

    def mul(value:Int): AABB = new AABB(minX * value, minY * value, minZ * value, maxX * value, maxY * value, maxZ * value)

    def calculateIntercept(vecA: Vec3f, vecB: Vec3f): RayTraceResult = {

        var enumfacing:BlockDirection = BlockDirection.WEST

        var vec3d = this.checkX(this.minX, vecA, vecB)
        var vec3d1 = this.checkX(this.maxX, vecA, vecB)
        if (vec3d1 != null && this.checkDistance(vecA, vec3d, vec3d1)) {
            vec3d = vec3d1
            enumfacing = BlockDirection.EAST
        }
        vec3d1 = this.checkY(this.minY, vecA, vecB)
        if (vec3d1 != null && this.checkDistance(vecA, vec3d, vec3d1)) {
            vec3d = vec3d1
            enumfacing = BlockDirection.DOWN
        }
        vec3d1 = this.checkY(this.maxY, vecA, vecB)
        if (vec3d1 != null && this.checkDistance(vecA, vec3d, vec3d1)) {
            vec3d = vec3d1
            enumfacing = BlockDirection.UP
        }
        vec3d1 = this.checkZ(this.minZ, vecA, vecB)
        if (vec3d1 != null && this.checkDistance(vecA, vec3d, vec3d1)) {
            vec3d = vec3d1
            enumfacing = BlockDirection.NORTH
        }
        vec3d1 = this.checkZ(this.maxZ, vecA, vecB)
        if (vec3d1 != null && this.checkDistance(vecA, vec3d, vec3d1)) {
            vec3d = vec3d1
            enumfacing = BlockDirection.SOUTH
        }
        if (vec3d == null) null
        else new RayTraceResult(vec3d, enumfacing)
    }

    def checkDistance(vec3f1:Vec3f, vec3f2: Vec3f, vec3f3: Vec3f): Boolean = {
        vec3f2 == null || vec3f1.distanceSquared(vec3f3) < vec3f1.distanceSquared(vec3f2)
    }

    def checkX(x:Double, vec3f2: Vec3f, vec3f3: Vec3f): Vec3f = {
        val vec3d = vec3f2.getIntermediateWithXValue(vec3f3, x)
        if (vec3d != null && this.intersectsWithYZ(vec3d)) vec3d
        else null
    }

    def checkY(y:Double, vec3f2: Vec3f, vec3f3: Vec3f): Vec3f = {
        val vec3d = vec3f2.getIntermediateWithYValue(vec3f3, y)
        if (vec3d != null && this.intersectsWithXZ(vec3d)) vec3d
        else null
    }

    def checkZ(z:Double, vec3f2: Vec3f, vec3f3: Vec3f): Vec3f = {
        val vec3d = vec3f2.getIntermediateWithZValue(vec3f3, z)
        if (vec3d != null && this.intersectsWithXY(vec3d)) vec3d
        else null
    }

    def intersectsWithYZ(vec: Vec3f): Boolean = vec.y >= this.minY && vec.y <= this.maxY && vec.z >= this.minZ && vec.z <= this.maxZ

    def intersectsWithXZ(vec: Vec3f): Boolean = vec.x >= this.minX && vec.x <= this.maxX && vec.z >= this.minZ && vec.z <= this.maxZ

    def intersectsWithXY(vec: Vec3f): Boolean = vec.x >= this.minX && vec.x <= this.maxX && vec.y >= this.minY && vec.y <= this.maxY



}
