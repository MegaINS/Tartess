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

    def set(aabb: AABB): Unit = {
        this.minX = aabb.minX
        this.minY = aabb.minY
        this.minZ = aabb.minZ
        this.maxX = aabb.maxX
        this.maxY = aabb.maxY
        this.maxZ = aabb.maxZ
    }

//    def expand(x: Float, y: Float, z: Float): AABB = {
//        val d0 = this.minX - x
//        val d1 = this.minY - y
//        val d2 = this.minZ - z
//        val d3 = this.maxX + x
//        val d4 = this.maxY + y
//        val d5 = this.maxZ + z
//        new AABB(d0, d1, d2, d3, d4, d5)
//    }

    def expand(x: Float, y: Float, z: Float): AABB = {
        var x0 = minX
        var y0 = minY
        var z0 = minZ
        var x1 = maxX
        var y1 = maxY
        var z1 = maxZ
        if (x > 0.0) x1 += x
        else x0 += x
        if (y > 0.0) y1 += y
        else y0 += y
        if (z > 0.0) z1 += z
        else z0 += z
        new AABB(x0, y0, z0, x1, y1, z1)
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


    def checkXcollision(aabb: AABB, xIn: Float): Float = {
        var x = xIn
        if (minY < aabb.maxY && maxY > aabb.minY) if (minZ < aabb.maxZ && maxZ > aabb.minZ) {
            var max = .0f
            if (x > 0.0 && minX >= aabb.maxX - x) {
                max = minX - aabb.maxX
                if (max < x) x = max
            }
            if (x < 0.0 && maxX <= aabb.minX - x) {
                max = maxX - aabb.minX
                if (max > x) x = max
            }
        }
        x
    }

    def checkYcollision(aabb: AABB, yIn: Float): Float = {
        var y = yIn
        if (minX < aabb.maxX && maxX > aabb.minX) if (minZ < aabb.maxZ && maxZ > aabb.minZ) {
            var max = .0f
            if (y > 0.0 && minY >= aabb.maxY) {
                max = minY - aabb.maxY
                if (max < y) y = max
            }
            if (y < 0.0 && maxY <= aabb.minY) {
                max = maxY - aabb.minY
                if (max > y) y = max
            }
        }
        y
    }

    def checkZcollision(aabb: AABB, zIn: Float): Float = {
        var z = zIn
        if (minY < aabb.maxY && maxY > aabb.minY) if (minX < aabb.maxX && maxX > aabb.minX) {
            var max = .0f
            if (z > 0.0 && minZ >= aabb.maxZ - z) {
                max = minZ - aabb.maxZ
                if (max < z) z = max
            }
            if (z < 0.0 && maxZ <= aabb.minZ - z) {
                max = maxZ - aabb.minZ
                if (max > z) z = max
            }
        }
        z
    }



    def canEqual(other: Any): Boolean = other.isInstanceOf[AABB]

    override def equals(other: Any): Boolean = other match {
        case that: AABB =>
            (that canEqual this) &&
                    minX == that.minX &&
                    minY == that.minY &&
                    minZ == that.minZ &&
                    maxX == that.maxX &&
                    maxY == that.maxY &&
                    maxZ == that.maxZ
        case _ => false
    }

}
