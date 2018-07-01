package ru.megains.tartess.block

import ru.megains.tartess.block.data.BlockState
import ru.megains.tartess.physics.AABB
import ru.megains.tartess.renderer.texture.TTextureRegister

object BlockAir extends Block("air") {

    override def isOpaqueCube: Boolean = false

    override def registerTexture(textureRegister: TTextureRegister): Unit = {}

    override def getSelectedBoundingBox(blockState: BlockState): AABB = Block.NULL_AABB
}
