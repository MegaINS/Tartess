package ru.megains.tartess.common.register

import ru.megains.tartess.client.Tartess
import ru.megains.tartess.common.block._
import ru.megains.tartess.common.entity.item.EntityItem
import ru.megains.tartess.common.entity.mob.EntityCube
import ru.megains.tartess.common.item.{ItemMass, ItemSingle, ItemStack}
import ru.megains.tartess.common.tileentity.TileEntityChest
import ru.megains.tartess.common.utils.Logger

object Bootstrap extends Logger[Tartess] {

    var isNotInit = true

    def init(): Unit = {
        if (isNotInit) {
            isNotInit = false
            log.info("Blocks init...")
            initBlocks(GameRegister)
            log.info("Items init...")
            initItems(GameRegister)
            log.info("TileEntity init...")
            initTileEntity(GameRegister)
            log.info("Entity init...")
            initEntity(GameRegister)
        }

    }

    def initBlocks(gameRegister:TGameRegister): Unit = {

        gameRegister.registerBlock(0, BlockAir)
        gameRegister.registerBlock(1, new BlockWG("stone"))
        gameRegister.registerBlock(2, new BlockWG("dirt"))

        gameRegister.registerBlock(3, new BlockTest("test0",0))
        gameRegister.registerBlock(4, new BlockTest("test1",1))
        gameRegister.registerBlock(5, new BlockTest("test2",2))
        gameRegister.registerBlock(6, new BlockStare("stare",0))
        gameRegister.registerBlock(7, new BlockStare("stare1",1))

        gameRegister.registerBlock(8,new BlockGrass("grass"))


        gameRegister.registerBlock(15, new BlockTest("test12",12))
        gameRegister.registerBlock(16, new BlockTest("test13",13))
        gameRegister.registerBlock(17, new BlockTest("test14",14))
        gameRegister.registerBlock(18, new BlockGlass("glass"))
        gameRegister.registerBlock(19, new BlockChest("chest"))
        gameRegister.registerBlock(20, new BlockRotateTexture())
      /*    GameRegister.registerBlock(6, new BlockTest("test3",3))
          GameRegister.registerBlock(7, new BlockTest("test4",4))
         GameRegister.registerBlock(8, new BlockTest("test5",5))
          GameRegister.registerBlock(9, new BlockTest("test6",6))
          GameRegister.registerBlock(10, new BlockTest("test7",7))
          GameRegister.registerBlock(11, new BlockTest("test8",8))
          GameRegister.registerBlock(12, new BlockTest("test9",9))
         GameRegister.registerBlock(13, new BlockTest("test10",10))
         GameRegister.registerBlock(14, new BlockTest("test11",11))
        GameRegister.registerBlock(21, new BlockTest("test15",15))
        GameRegister.registerBlock(22, new BlockTest("test16",16))
        GameRegister.registerBlock(23, new BlockTest("test17",17))*/
    }

    def initItems(gameRegister:TGameRegister): Unit = {
        gameRegister.registerItem(1000, new ItemStack("stick"))
        gameRegister.registerItem(1001, new ItemMass("coal"))
        gameRegister.registerItem(1002, new ItemSingle("helmet"))
    }

    def initTileEntity(gameRegister:TGameRegister): Unit = {
        gameRegister.registerTileEntity(0,classOf[TileEntityChest])
       // GameRegister.registerTileEntityRender(classOf[TileEntityChest],RenderChest)
    }
    def initEntity(gameRegister:TGameRegister): Unit = {
        gameRegister.registerEntity(0,classOf[EntityItem])
        gameRegister.registerEntity(1,classOf[EntityCube])
    }
}
