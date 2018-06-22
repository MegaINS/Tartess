package ru.megains.old.physics

import ru.megains.old.blockdata.BlockSize


class BlockAxisAlignedBB(minX:BlockSize,
                         minY: BlockSize,
                         minZ: BlockSize,
                         maxX: BlockSize,
                         maxY: BlockSize,
                         maxZ: BlockSize) extends AxisAlignedBB(
                         minX.value,
                         minY.value,
                         minZ.value,
                         maxX.value,
                         maxY.value,
                         maxZ.value) {








}
