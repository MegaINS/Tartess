package ru.megains.tartess.common.register

import ru.megains.tartess.client.Tartess
import ru.megains.tartess.client.renderer.block.RenderBlockGrass
import ru.megains.tartess.common.block._
import ru.megains.tartess.common.entity.item.EntityItem
import ru.megains.tartess.common.entity.mob.EntityCube
import ru.megains.tartess.common.item.{ItemMass, ItemSingle, ItemStack}
import ru.megains.tartess.client.renderer.entity.RenderEntityCube
import ru.megains.tartess.common.tileentity.TileEntityChest
import ru.megains.tartess.common.utils.Logger

object Bootstrap extends Logger[Tartess] {
    var isNotInit = true

    def init(): Unit = {
        if (isNotInit) {
            isNotInit = false
            log.info("Blocks init...")
            initBlocks()
            log.info("Items init...")
            initItems()
            log.info("TileEntity init...")
            initTileEntity()
            log.info("Entity init...")
            initEntity()
        }

    }

    def initBlocks(): Unit = {

        GameRegister.registerBlock(0, BlockAir)
        GameRegister.registerBlock(1, new BlockWG("stone"))
        GameRegister.registerBlock(2, new BlockWG("dirt"))

        GameRegister.registerBlock(3, new BlockTest("test0",0))
        GameRegister.registerBlock(4, new BlockTest("test1",1))
        GameRegister.registerBlock(5, new BlockTest("test2",2))
        GameRegister.registerBlock(6, new BlockStare("stare",0))
        GameRegister.registerBlock(7, new BlockStare("stare1",1))

        val blockGrass = new BlockGrass("grass")

        GameRegister.registerBlock(8,blockGrass,new RenderBlockGrass(blockGrass))

      //  GameRegister.registerBlock(6, new BlockTest("test3",3))
      //  GameRegister.registerBlock(7, new BlockTest("test4",4))
       // GameRegister.registerBlock(8, new BlockTest("test5",5))
      //  GameRegister.registerBlock(9, new BlockTest("test6",6))
      //  GameRegister.registerBlock(10, new BlockTest("test7",7))
      //  GameRegister.registerBlock(11, new BlockTest("test8",8))
      //  GameRegister.registerBlock(12, new BlockTest("test9",9))
       // GameRegister.registerBlock(13, new BlockTest("test10",10))
       // GameRegister.registerBlock(14, new BlockTest("test11",11))
        GameRegister.registerBlock(15, new BlockTest("test12",12))
        GameRegister.registerBlock(16, new BlockTest("test13",13))
        GameRegister.registerBlock(17, new BlockTest("test14",14))
        GameRegister.registerBlock(18, new BlockGlass("glass"))
        GameRegister.registerBlock(19, new BlockChest("chest"))
        GameRegister.registerBlock(20, new BlockRotateTexture())
      //  GameRegister.registerBlock(21, new BlockTest("test15",15))
      //  GameRegister.registerBlock(22, new BlockTest("test16",16))
       // GameRegister.registerBlock(23, new BlockTest("test17",17))
    }

    def initItems(): Unit = {
        GameRegister.registerItem(1000, new ItemStack("stick"))
        GameRegister.registerItem(1001, new ItemMass("coal"))
        GameRegister.registerItem(1002, new ItemSingle("helmet"))
    }

    def initTileEntity(): Unit = {
        GameRegister.registerTileEntity(0,classOf[TileEntityChest])
       // GameRegister.registerTileEntityRender(classOf[TileEntityChest],RenderChest)
    }
    def initEntity(): Unit = {
        GameRegister.registerEntity(0,classOf[EntityItem])
        GameRegister.registerEntity(1,classOf[EntityCube],RenderEntityCube)
    }
}
