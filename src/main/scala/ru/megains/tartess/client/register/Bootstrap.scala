package ru.megains.tartess.client.register

import ru.megains.tartess.client.renderer.block.RenderBlockGrass
import ru.megains.tartess.client.renderer.entity.RenderEntityCube
import ru.megains.tartess.common.entity.mob.EntityCube
import ru.megains.tartess.common.register.Bootstrap._

object Bootstrap {


    def init(): Unit = {
        if (isNotInit) {
            isNotInit = false
            log.info("Blocks init...")
            initBlocks(GameRegister)
            log.info("Blocks render init...")
            initBlocksRender()
            log.info("Items init...")
            initItems(GameRegister)
            log.info("Items render init...")
            initItemsRender()
            log.info("TileEntity init...")
            initTileEntity(GameRegister)
            log.info("TileEntity render init...")
            initTileEntityRender()
            log.info("Entity init...")
            initEntity(GameRegister)
            log.info("Entity render init...")
            initEntityRender()
        }

    }
    def initBlocksRender(): Unit = {
        GameRegister.registerBlockRender("grass",new RenderBlockGrass("grass"))
    }

    def initItemsRender(): Unit = {

    }

    def initTileEntityRender(): Unit = {

        // GameRegister.registerTileEntityRender(classOf[TileEntityChest],RenderChest)
    }
    def initEntityRender(): Unit = {


        GameRegister.registerEntityRender(classOf[EntityCube],RenderEntityCube)
    }
}
