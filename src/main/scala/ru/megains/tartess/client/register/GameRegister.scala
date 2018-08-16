package ru.megains.tartess.client.register

import ru.megains.tartess.client.renderer.api.{TRenderBlock, TRenderEntity, TRenderItem, TRenderTileEntity}
import ru.megains.tartess.client.renderer.block.{RenderBlockStandart, RenderBlockWG}
import ru.megains.tartess.client.renderer.item.{RenderItemBlock, RenderItemStandart}
import ru.megains.tartess.common.block.{Block, BlockWG}
import ru.megains.tartess.common.entity.Entity
import ru.megains.tartess.common.item.{Item, ItemBlock}
import ru.megains.tartess.common.register.{RegisterRender, TGameRegister, GameRegister => CommonGameRegister}
import ru.megains.tartess.common.tileentity.TileEntity

object GameRegister extends TGameRegister{


    val blockData = new RegisterRender[TRenderBlock]

    val itemData = new RegisterRender[TRenderItem]

    val tileEntityData = new RegisterRender[TRenderTileEntity]

    val entityData = new  RegisterRender[TRenderEntity]

    def registerBlockRender(block: Block, renderBlock: TRenderBlock): Unit = {
        val id: Int = CommonGameRegister.getIdByBlock(block)
        if (id != -1) {
            blockData.registerRender(id, renderBlock)
        } else {
            println("Block +\"" + block.name + "\" not register")
        }
    }



    def registerBlockRender(name: String, renderBlock: TRenderBlock): Unit = {
        val id: Int = CommonGameRegister.getIdByBlockName(name)
        if (id != -1) {
            blockData.registerRender(id, renderBlock)
        } else {
            println("Block +\"" + name + "\" not register")
        }
    }
    def registerItemRender(item: Item , tRenderItem: TRenderItem): Unit = {
        val id: Int = CommonGameRegister.getIdByItem(item)
        if (id != -1) {
            itemData.registerRender(id, tRenderItem)
        } else {
            println("Block +\"" + item.name + "\" not register")
        }
    }
    def registerTileEntityRender(tileEntity: Class[_<:TileEntity], aRenderBlock: TRenderTileEntity): Unit = {
        val id: Int = CommonGameRegister.getIdByTileEntity(tileEntity)
        if (id != -1) {
            tileEntityData.registerRender(id, aRenderBlock)
        } else {
            println("Block +\"" + tileEntity.toString + "\" not register")
        }
    }

    def registerEntityRender(tileEntity: Class[_<:Entity], aRenderBlock: TRenderEntity): Unit = {
        val id: Int = CommonGameRegister.getIdByEntity(tileEntity)
        if (id != -1) {
            entityData.registerRender(id, aRenderBlock)
        } else {
            println("Block +\"" + tileEntity.toString + "\" not register")
        }
    }



    def registerEntity(id: Int, tileEntity: Class[_<:Entity],tRenderEntity: TRenderEntity): Unit = {
        if(ru.megains.tartess.common.register.GameRegister.registerEntity(id, tileEntity)){
            registerEntityRender(tileEntity,tRenderEntity)
        }
    }
    def registerBlock(id: Int, block: Block): Boolean = {
        if (CommonGameRegister.registerBlock(id, block)) {
            block match {
                case blockWG:BlockWG =>  registerBlockRender(block,RenderBlockWG(blockWG))
                case _ =>   registerBlockRender(block,RenderBlockStandart(block))
            }
            if(block.name != "air") {
                val item = CommonGameRegister.getItemById(id)
                registerItemRender(item, new RenderItemBlock(item.asInstanceOf[ItemBlock]))
            }
            true
        }else{
            false
        }
    }
    def registerBlock(id: Int, block: Block,tRenderBlock: TRenderBlock): Unit = {
        if (CommonGameRegister.registerBlock(id, block)) {
            registerBlockRender(block,tRenderBlock)
            val item = new ItemBlock(block)
            registerItem(id, item, new RenderItemBlock(item))
        }
    }
    def registerItem(id: Int, item: Item): Boolean = {
        if (ru.megains.tartess.common.register.GameRegister.registerItem(id, item)) {
            registerItemRender(item, new RenderItemStandart(item))
            true
        }else{
            false
        }
    }
    def registerItem(id: Int, item: Item,tRenderItem: TRenderItem): Boolean = {
        if (ru.megains.tartess.common.register.GameRegister.registerItem(id, item)) {
            registerItemRender(item,tRenderItem)
            true
        }else{
            false
        }
    }

    def registerTileEntity(id: Int, tileEntity: Class[_<:TileEntity],tRenderTileEntity: TRenderTileEntity): Unit = {
        if(ru.megains.tartess.common.register.GameRegister.registerTileEntity(id, tileEntity)){
            registerTileEntityRender(tileEntity,tRenderTileEntity)
        }

    }

    def registerTileEntity(id: Int, tileEntity: Class[_<:TileEntity]): Boolean = {
        ru.megains.tartess.common.register.GameRegister.registerTileEntity(id, tileEntity)
    }
    def registerEntity(id: Int, tileEntity: Class[_<:Entity]): Boolean = {
        ru.megains.tartess.common.register.GameRegister.registerEntity(id, tileEntity)
    }

    def getBlockRender(block: Block): TRenderBlock = {blockData.getRender(CommonGameRegister.getIdByBlock(block))}
    def getItemRender(item: Item): TRenderItem = itemData.getRender(CommonGameRegister.getIdByItem(item))
    def getTileEntityRender(tileEntity: TileEntity): TRenderTileEntity = tileEntityData.getRender(CommonGameRegister.getIdByTileEntity(tileEntity.getClass))
    def getEntityRender(entity:Entity): TRenderEntity = entityData.getRender(CommonGameRegister.getIdByEntity( entity.getClass))
}
