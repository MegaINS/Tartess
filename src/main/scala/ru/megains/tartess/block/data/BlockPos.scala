package ru.megains.tartess.block.data

import ru.megains.tartess.world.World

class BlockPos(val x:Int,val y:Int,val z:Int) {

    def rendX: Float = (x&127) / 8f
    def rendY: Float = (y&127) / 8f
    def rendZ: Float = (z&127) / 8f

    def isValid(world: World): Boolean = !(z < -world.width*128 || y < -world.height*128 || x < -world.length*128) && !(z > world.width*128 - 1 || y > world.height*128 - 1 || x > world.length*128 - 1)
}
