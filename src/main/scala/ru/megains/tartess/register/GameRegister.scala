package ru.megains.tartess.register

import ru.megains.tartess.block.Block
import ru.megains.tartess.item.{Item, ItemBlock}
import ru.megains.tartess.renderer.api.{RenderBlock, RenderItem}
import ru.megains.tartess.renderer.block.RenderBlockWG
import ru.megains.tartess.renderer.item.{RenderItemBlock, RenderItemStandart}

object GameRegister {

    private val blockData = new RegisterNamespace[Block] with RegisterRender[RenderBlock] {
        override val default: RenderBlock = RenderBlockWG
    }
    private val itemData = new RegisterNamespace[Item] with RegisterRender[RenderItem] {
        override val default = null
    }


    def registerBlock(id: Int, block: Block): Unit = {
        if (privateRegisterBlock(id, block)) {

            val item = new ItemBlock(block)

            if (privateRegisterItem(id, item)) {
                itemData.registerRender(id, new RenderItemBlock(item))
            }
        }
    }

    private def privateRegisterBlock(id: Int, block: Block): Boolean = {
        if (blockData.contains(block)) {
            println("Block \"" + block.name + "\" already register")
        } else {
            if (blockData.contains(id)) {
                println("Id \"" + id + "\" not single")
            } else {
                if (blockData.contains(block.name)) {
                    println("Name \"" + block.name + "\" not single")
                } else {
                    blockData.registerObject(id, block.name, block)
                    return true
                }
            }
        }
        false
    }

    def getBlockRender(block: Block): RenderBlock = blockData.getRender(getIdByBlock(block))

    def registerBlockRender(block: Block, renderBlock: RenderBlock): Unit = {
        val id: Int = getIdByBlock(block)
        if (id != -1) {
            blockData.registerRender(id, renderBlock)
        } else {
            println("Block +\"" + block.name + "\" not register")
        }
    }

    def registerItem(id: Int, item: Item): Unit = {
        if (privateRegisterItem(id, item)) {
            itemData.registerRender(id, new RenderItemStandart(item))
        }
    }

    private def privateRegisterItem(id: Int, item: Item): Boolean = {
        if (itemData.contains(item)) {
            println("Item \"" + item.name + "\" already register")
        } else {
            if (itemData.contains(id)) {
                println("Id \"" + id + "\" not single")
            } else {
                if (itemData.contains(item.name)) {
                    println("Name \"" + item.name + "\" not single")
                } else {
                    itemData.registerObject(id, item.name, item)

                    return true
                }
            }
        }
        false
    }

    def getIdByBlock(block: Block): Int = blockData.getIdByObject(block)

    def getBlockById(id: Int): Block = blockData.getObject(id)

    def getBlockByName(name: String): Block = blockData.getObject(name)

    def getBlocks: Iterable[Block] = blockData.getObjects

    def getItemRender(item: Item): RenderItem = itemData.getRender(getIdByItem(item))

    def getIdByItem(item: Item): Int = itemData.getIdByObject(item)

    def getItemById(id: Int): Item = itemData.getObject(id)

    def getItems: Iterable[Item] = itemData.getObjects

    def getItemFromBlock(block: Block): Item = itemData.getObject(blockData.getIdByObject(block))
}
