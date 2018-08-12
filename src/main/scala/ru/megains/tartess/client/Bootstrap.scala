package ru.megains.tartess.client

import ru.megains.tartess.client.renderer.block.RenderBlockGrass
import ru.megains.tartess.client.renderer.entity.RenderEntityCube
import ru.megains.tartess.common.entity.mob.EntityCube
import ru.megains.tartess.common.register.Bootstrap._
import ru.megains.tartess.common.register.GameRegister
object Bootstrap {


    def init(): Unit = {
        if (isNotInit) {
            isNotInit = false
            log.info("Blocks init...")
            initBlocks()
            log.info("Blocks render init...")
            initBlocksRender()
            log.info("Items init...")
            initItems()
            log.info("Items render init...")
            initItemsRender()
            log.info("TileEntity init...")
            initTileEntity()
            log.info("TileEntity render init...")
            initTileEntityRender()
            log.info("Entity init...")
            initEntity()
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
