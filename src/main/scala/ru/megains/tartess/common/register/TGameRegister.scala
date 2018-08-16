package ru.megains.tartess.common.register

import ru.megains.tartess.common.block.Block
import ru.megains.tartess.common.entity.Entity
import ru.megains.tartess.common.item.Item
import ru.megains.tartess.common.tileentity.TileEntity

trait TGameRegister {

    def registerBlock(id: Int, block: Block): Boolean
    def registerItem(id: Int, item: Item): Boolean
    def registerTileEntity(id: Int, tileEntity: Class[_<:TileEntity]): Boolean
    def registerEntity(id: Int, tileEntity: Class[_<:Entity]): Boolean
}
