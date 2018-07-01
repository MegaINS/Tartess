package ru.megains.tartess.block.data

import ru.megains.tartess.world.World

class BlockPos(val x:Int,val y:Int,val z:Int) {

    def rendX: Float = (x&255) / 16f

    def rendY: Float = (y&255) / 16f

    def rendZ: Float = (z&255) / 16f

    def sum(inX: Int, inY: Int, inZ: Int) = new BlockPos(inX + x, inY + y, inZ + z)

    def isValid(world: World): Boolean =
        !(
             z < -world.width  ||
             y < -world.height ||
             x < -world.length
         )&&
        !(
             z >  world.width  ||
             y >  world.height ||
             x >  world.length
         )
}
