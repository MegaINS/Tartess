package ru.megains.tartess.register

import ru.megains.tartess.Tartess
import ru.megains.tartess.block._
import ru.megains.tartess.renderer.block.RenderBlockStandart
import ru.megains.tartess.tileentity.TileEntityChest
import ru.megains.tartess.utils.Logger

object Bootstrap extends Logger[Tartess] {
    var isNotInit = true

    def init(): Unit = {
        if (isNotInit) {
            isNotInit = false
            log.info("Blocks init...")
            initBlocks()
          //  log.info("Items init...")
          //  initItems()
           // log.info("TileEntity init...")
          //  initTileEntity()
           // log.info("Entity init...")
           // initEntity()
        }

    }

    def initBlocks(): Unit = {

        GameRegister.registerBlock(0, BlockAir)
        GameRegister.registerBlock(1, new Block("stone"))
        GameRegister.registerBlock(2, new Block("dirt"))

        GameRegister.registerBlock(3, new BlockTest("test0",0))
        GameRegister.registerBlock(4, new BlockTest("test1",1))
        GameRegister.registerBlock(5, new BlockTest("test2",2))
        GameRegister.registerBlock(6, new BlockTest("test3",3))
        GameRegister.registerBlock(7, new BlockTest("test4",4))
        GameRegister.registerBlock(8, new BlockTest("test5",5))
        GameRegister.registerBlock(9, new BlockTest("test6",6))
        GameRegister.registerBlock(10, new BlockTest("test7",7))
        GameRegister.registerBlock(11, new BlockTest("test8",8))
        GameRegister.registerBlock(12, new BlockTest("test9",9))
        GameRegister.registerBlock(13, new BlockTest("test10",10))
        GameRegister.registerBlock(14, new BlockTest("test11",11))
        GameRegister.registerBlock(15, new BlockTest("test12",12))
        GameRegister.registerBlock(16, new BlockTest("test13",13))
        GameRegister.registerBlock(17, new BlockTest("test14",14))


        GameRegister.registerBlock(18,new BlockGlass("glass"))
        GameRegister.registerBlock(19, new BlockChest("tileEntityTest"))

        GameRegister.registerBlockRender(Blocks.glass,RenderBlockStandart)
        GameRegister.registerBlockRender(Blocks.test0,RenderBlockStandart)
        GameRegister.registerBlockRender(Blocks.test1,RenderBlockStandart)
        GameRegister.registerBlockRender(Blocks.test2,RenderBlockStandart)

    }

    def initTileEntity(): Unit = {
        GameRegister.registerTileEntity(0,classOf[TileEntityChest])
    }

}
