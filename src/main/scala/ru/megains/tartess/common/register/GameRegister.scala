package ru.megains.tartess.common.register

import ru.megains.tartess.common.block.{Block, BlockWG}
import ru.megains.tartess.common.entity.Entity
import ru.megains.tartess.common.item.{Item, ItemBlock}
import ru.megains.tartess.client.renderer.api.{TRenderBlock, TRenderEntity, TRenderItem, TRenderTileEntity}
import ru.megains.tartess.client.renderer.block.{RenderBlockStandart, RenderBlockWG}
import ru.megains.tartess.client.renderer.item.{RenderItemBlock, RenderItemStandart}
import ru.megains.tartess.common.tileentity.TileEntity

object GameRegister {

    val blockData = new RegisterNamespace[Block] with RegisterRender[TRenderBlock]

    val itemData = new RegisterNamespace[Item] with RegisterRender[TRenderItem]

    val tileEntityData = new RegisterNamespace[Class[_<:TileEntity]] with RegisterRender[TRenderTileEntity]

    val entityData = new RegisterNamespace[Class[_<:Entity]] with RegisterRender[TRenderEntity]

    def registerBlock(id: Int, block: Block): Unit = {
        if (privateRegisterBlock(id, block)) {
            block match {
                case blockWG:BlockWG =>  registerBlockRender(block,RenderBlockWG(blockWG))
                case _ =>   registerBlockRender(block,RenderBlockStandart(block))
            }
            if(block.name != "air"){
                val item = new ItemBlock(block)
                registerItem(id, item,new RenderItemBlock(item))
            }
        }
    }
    def registerBlock(id: Int, block: Block,tRenderBlock: TRenderBlock): Unit = {
        if (privateRegisterBlock(id, block)) {
            registerBlockRender(block,tRenderBlock)
            val item = new ItemBlock(block)
            registerItem(id, item, new RenderItemBlock(item))
        }
    }
    def registerItem(id: Int, item: Item): Unit = {
        if (privateRegisterItem(id, item)) {
            registerItemRender(item, new RenderItemStandart(item))
        }
    }
    def registerItem(id: Int, item: Item,tRenderItem: TRenderItem): Unit = {
        if (privateRegisterItem(id, item)) {
            registerItemRender(item,tRenderItem)
        }
    }
    def registerTileEntity(id: Int, tileEntity: Class[_<:TileEntity]): Unit = {
        privateRegisterTileEntity(id, tileEntity)
    }
    def registerTileEntity(id: Int, tileEntity: Class[_<:TileEntity],tRenderTileEntity: TRenderTileEntity): Unit = {
        if(privateRegisterTileEntity(id, tileEntity)){
            registerTileEntityRender(tileEntity,tRenderTileEntity)
        }

    }
    def registerEntity(id: Int, tileEntity: Class[_<:Entity]): Unit = {
        privateRegisterEntity(id, tileEntity)
    }
    def registerEntity(id: Int, tileEntity: Class[_<:Entity],tRenderEntity: TRenderEntity): Unit = {
        if(privateRegisterEntity(id, tileEntity)){
            registerEntityRender(tileEntity,tRenderEntity)
        }
    }


    def registerBlockRender(block: Block, renderBlock: TRenderBlock): Unit = {
        val id: Int = getIdByBlock(block)
        if (id != -1) {
            blockData.registerRender(id, renderBlock)
        } else {
            println("Block +\"" + block.name + "\" not register")
        }
    }
    def registerItemRender(item: Item , tRenderItem: TRenderItem): Unit = {
        val id: Int = getIdByItem(item)
        if (id != -1) {
            itemData.registerRender(id, tRenderItem)
        } else {
            println("Block +\"" + item.name + "\" not register")
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
