package ru.megains.tartess.register

import ru.megains.tartess.block.Block
import ru.megains.tartess.entity.Entity
import ru.megains.tartess.item.{Item, ItemBlock}
import ru.megains.tartess.renderer.api.{TRenderBlock, TRenderEntity, TRenderItem, TRenderTileEntity}
import ru.megains.tartess.renderer.block.RenderBlockWG
import ru.megains.tartess.renderer.item.{RenderItemBlock, RenderItemStandart}
import ru.megains.tartess.tileentity.TileEntity

object GameRegister {

    val blockData = new RegisterNamespace[Block] with RegisterRender[TRenderBlock] {
        override val default: TRenderBlock = RenderBlockWG
    }

    val itemData = new RegisterNamespace[Item] with RegisterRender[TRenderItem] {
        override val default:TRenderItem = null
    }

    val tileEntityData = new RegisterNamespace[Class[_<:TileEntity]] with RegisterRender[TRenderTileEntity] {
        override val default:TRenderTileEntity = null
    }

    val entityData = new RegisterNamespace[Class[_<:Entity]] with RegisterRender[TRenderEntity]{
        override val default:TRenderEntity = null
    }

    def registerBlock(id: Int, block: Block): Unit = {
        if (privateRegisterBlock(id, block)) {
            if(block.name != "air"){
                val item = new ItemBlock(block)
                if (privateRegisterItem(id, item)) {
                    itemData.registerRender(id, new RenderItemBlock(item))
                }
            }
        }
    }
    def registerItem(id: Int, item: Item): Unit = {
        if (privateRegisterItem(id, item)) {
            itemData.registerRender(id, new RenderItemStandart(item))
        }
    }
    def registerTileEntity(id: Int, tileEntity: Class[_<:TileEntity]): Unit = {
        privateRegisterTileEntity(id, tileEntity)
    }
    def registerEntity(id: Int, tileEntity: Class[_<:Entity]): Unit = {
        privateRegisterEntity(id, tileEntity)
    }



    def registerBlockRender(block: Block, renderBlock: TRenderBlock): Unit = {
        val id: Int = getIdByBlock(block)
        if (id != -1) {
            blockData.registerRender(id, renderBlock)
        } else {
            println("Block +\"" + block.name + "\" not register")
        }
    }

    def registerTileEntityRender(tileEntity: Class[_<:TileEntity], aRenderBlock: TRenderTileEntity): Unit = {
        val id: Int = getIdByTileEntity(tileEntity)
        if (id != -1) {
            tileEntityData.registerRender(id, aRenderBlock)
        } else {
            println("Block +\"" + tileEntity.toString + "\" not register")
        }
    }

    def registerEntityRender(tileEntity: Class[_<:Entity], aRenderBlock: TRenderEntity): Unit = {
        val id: Int = getIdByEntity(tileEntity)
        if (id != -1) {
            entityData.registerRender(id, aRenderBlock)
        } else {
            println("Block +\"" + tileEntity.toString + "\" not register")
        }
    }




    def getTileEntityById(id: Int):Class[_<:TileEntity] = {
        tileEntityData.getObject(id)
    }

    def getIdByTileEntity(tileEntity: Class[_ <: TileEntity]): Int = {
        tileEntityData.getIdByObject(tileEntity)
    }

    def getEntityById(id: Int):Class[_<:Entity] = {
        entityData.getObject(id)
    }

    def getIdByEntity(tileEntity: Class[_ <: Entity]): Int = {
        entityData.getIdByObject(tileEntity)
    }

    def getIdByBlock(block: Block): Int = blockData.getIdByObject(block)

    def getBlockById(id: Int): Block = blockData.getObject(id)

    def getBlockByName(name: String): Block = blockData.getObject(name)

    def getIdByItem(item: Item): Int = itemData.getIdByObject(item)

    def getItemById(id: Int): Item = itemData.getObject(id)

    def getItemFromBlock(block: Block): Item = itemData.getObject(blockData.getIdByObject(block))








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
    private def privateRegisterTileEntity(id: Int, tileEntity: Class[_<:TileEntity]): Boolean = {
        if (tileEntityData.contains(tileEntity)) {
            println("TileEntity \"" + tileEntity.toString + "\" already register")
        } else {
            if (tileEntityData.contains(id)) {
                println("Id \"" + id + "\" not single")
            } else {
                if (tileEntityData.contains(tileEntity.toString)) {
                    println("Name \"" + tileEntity.toString + "\" not single")
                } else {
                    tileEntityData.registerObject(id, tileEntity.toString, tileEntity)
                    return true
                }
            }
        }
        false
    }
    private def privateRegisterEntity(id: Int, tileEntity: Class[_<:Entity]): Boolean = {
        if (entityData.contains(tileEntity)) {
            println("Entity \"" + tileEntity.toString + "\" already register")
        } else {
            if (entityData.contains(id)) {
                println("Id \"" + id + "\" not single")
            } else {
                if (entityData.contains(tileEntity.toString)) {
                    println("Name \"" + tileEntity.toString + "\" not single")
                } else {
                    entityData.registerObject(id, tileEntity.toString, tileEntity)
                    return true
                }
            }
        }
        false
    }

    def getBlocks: Iterable[Block] = blockData.getObjects
    def getItems: Iterable[Item] = itemData.getObjects
    def getTileEntities: Iterable[Class[_ <: TileEntity]] = tileEntityData.getObjects
    def getEntities: Iterable[Class[_ <: Entity]] = entityData.getObjects

    def getBlockRender(block: Block): TRenderBlock = blockData.getRender(getIdByBlock(block))
    def getItemRender(item: Item): TRenderItem = itemData.getRender(getIdByItem(item))
    def getTileEntityRender(tileEntity: TileEntity): TRenderTileEntity = tileEntityData.getRender(getIdByTileEntity(tileEntity.getClass))
    def getEntityRender(entity:Entity): TRenderEntity = entityData.getRender(getIdByEntity( entity.getClass))
}
