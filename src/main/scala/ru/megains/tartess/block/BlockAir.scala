package ru.megains.tartess.block

import ru.megains.tartess.block.data.BlockState

object BlockAir extends Block("air") {
    //override def isOpaqueCube: Boolean = false

    // override def registerTexture(textureRegister: TTextureRegister): Unit = {}

    override def getSelectedBoundingBox(blockState: BlockState) = Block.NULL_AABB
}
