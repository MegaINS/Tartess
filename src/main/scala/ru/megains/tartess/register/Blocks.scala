package ru.megains.tartess.register

import ru.megains.tartess.block.Block

object Blocks {



    lazy val air:Block = GameRegister.getBlockByName("air")
    lazy val dirt:Block = GameRegister.getBlockByName("dirt")
    lazy val stone:Block = GameRegister.getBlockByName("stone")


    def getBlockById(id: Int): Block = {
        GameRegister.getBlockById(id)
    }

    def getIdByBlock(block: Block): Int = {
        GameRegister.getIdByBlock(block)
    }

}
