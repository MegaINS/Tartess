package ru.megains.tartess.common.physics

import ru.megains.tartess.common.block.data.BlockDirection
import ru.megains.tartess.common.utils.Vec3f

import scala.collection.mutable

class AABBs(val hashSet: mutable.HashSet[AABB]) {



    def rotate(side: BlockDirection): AABBs = new AABBs(hashSet.map(_.rotate(side,this)))

    def sum(x: Float, y: Float, z: Float) =new AABBs(hashSet.map(_.sum(x, y, z)))

    def div(value: Float) = new AABBs(hashSet.map(_.div(value)))
    def maxX: Float = {
        var maxX:Float = Float.MinValue
        hashSet.foreach{a =>maxX = maxX.max(a.maxX)}
        maxX
    }
    def maxY: Float = {
        var maxY:Float = Float.MinValue
        hashSet.foreach{ a =>maxY = maxY.max(a.maxY)}
        maxY
    }
    def maxZ: Float = {
        var maxZ:Float = Float.MinValue
        hashSet.foreach{ a =>maxZ = maxZ.max(a.maxZ)}
        maxZ
    }
    def minX: Float = {
        var minX:Float = Float.MaxValue
        hashSet.foreach{ a =>minX = minX.min(a.minX)}
        minX
    }
    def minY: Float = {
        var minY:Float = Float.MaxValue
        hashSet.foreach{ a =>minY = minY.min(a.minY)}
        minY
    }
    def minZ: Float = {
        var minZ:Float = Float.MaxValue
        hashSet.foreach{ a =>minZ = minZ.min(a.minZ)}
        minZ
    }

    def calculateIntercept(vecA: Vec3f, vecB: Vec3f): (Vec3f, BlockDirection) = {
        var res:(Vec3f,BlockDirection) = null
        for(blockBody<- hashSet){
            val rayTraceResult = blockBody.calculateIntercept(vecA, vecB)
            if (rayTraceResult != null) {
                res =  rayTraceResult
            }
        }
        res
    }

    def checkXcollision(aabb: AABB, xIn: Float): Float = {
        var x = xIn
        hashSet.foreach(a => x = a.checkXcollision(aabb,x))
        x
    }

    def checkYcollision(aabb: AABB, yIn: Float): Float = {
        var y = yIn
        hashSet.foreach(a => y = a.checkYcollision(aabb,y))
        y
    }

    def checkZcollision(aabb: AABB, zIn: Float): Float = {
        var z = zIn
        hashSet.foreach(a => z = a.checkZcollision(aabb,z))
        z
    }



/*
        minX = minX.min(a.minX)
    minY = minY.min(a.minY)
    minZ = minZ.min(a.minZ)

    maxX = maxX.max(a.maxX)
    maxY = maxY.max(a.maxY)
    maxZ = maxZ.max(a.maxZ)
    def expand(x: Float, y: Float, z: Float): AABBs = {
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
        new AABBs(x0, y0, z0, x1, y1, z1)
    }




    def div(value: Float) = new AABBs(minX / value, minY / value, minZ / value, maxX / value, maxY / value, maxZ / value)

    def getCenterX: Float = (maxX + minX) / 2

    def getCenterZ: Float = (maxZ + minZ) / 2

    def getCopy = new AABBs(minX, minY, minZ, maxX, maxY, maxZ)

    def move(x: Float, y: Float, z: Float): AABBs = {
        minX += x
        minY += y
        minZ += z
        maxX += x
        maxY += y
        maxZ += z
        this
    }

    def mul(value:Int): AABBs = new AABBs(minX * value, minY * value, minZ * value, maxX * value, maxY * value, maxZ * value)



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







    override def equals(other: Any): Boolean = other match {
        case that: AABBs =>
                    minX == that.minX &&
                    minY == that.minY &&
                    minZ == that.minZ &&
                    maxX == that.maxX &&
                    maxY == that.maxY &&
                    maxZ == that.maxZ
        case _ => false
    }

    override def hashCode(): Int = {
        val state = Seq(minX, minY, minZ, maxX, maxY, maxZ)
        state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
    }
    */
}
