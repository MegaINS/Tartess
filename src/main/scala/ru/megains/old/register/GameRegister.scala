package ru.megains.old.register

import ru.megains.old.block.Block
import ru.megains.old.graph.renderer.api.ARenderBlock
import ru.megains.old.graph.renderer.block.RenderBlockStandart


object GameRegister {



    private val blockData = new RegisterNamespace[Block] with RegisterRender[ARenderBlock] {
        override val default = RenderBlockStandart

    }
//    private val itemData = new RegisterNamespace[Item] with RegisterRender[ARenderItem] {
//        override val default = null
//    }

    def registerBlock(id:Int, block: Block): Unit ={
        if(privateRegisterBlock(id,block)){

            //val item = new ItemBlock(block)

//            if(privateRegisterItem(id,item)){
//                itemData.registerRender(id,new RenderItemBlock(item))
//            }
        }
    }
//    def registerItem(id:Int,item: Item): Unit ={
//        if(privateRegisterItem(id,item)){
//            itemData.registerRender(id,new RenderItemStandart(item))
//        }
//    }


    private def privateRegisterBlock(id:Int, block: Block): Boolean ={
        if(blockData.contains(block)){
            println("Block \""+block.name + "\" already register")
        }else{
            if(blockData.contains(id)){
                println("Id \""+id + "\" not single")
            }else{
                if(blockData.contains(block.name)){
                    println("Name \""+ block.name + "\" not single")
                }else{
                    blockData.registerObject(id, block.name,block)
                   return true
                }
            }
        }
        false
    }



//    private def privateRegisterItem(id:Int,item: Item): Boolean ={
//        if(itemData.contains(item)){
//            println("Item \""+item.name + "\" already register")
//        }else{
//            if(itemData.contains(id)){
//                println("Id \""+id + "\" not single")
//            }else{
//                if(itemData.contains(item.name)){
//                    println("Name \""+ item.name + "\" not single")
//                }else{
//                    itemData.registerObject(id, item.name,item)
//
//                    return true
//                }
//            }
//        }
//        false
//    }
    
    def getBlocks = blockData.getObjects

    def getBlockById(id: Int): Block =  blockData.getObject(id)

    def getIdByBlock(block: Block): Int = blockData.getIdByObject(block)

    //def getIdByItem(item: Item): Int = itemData.getIdByObject(item)

   // def getItemById(id: Int): Item = itemData.getObject(id)



    def getBlockByName(name: String): Block = blockData.getObject(name)


    def registerBlockRender(block: Block,aRenderBlock: ARenderBlock): Unit = {
        val id:Int =  getIdByBlock(block)
        if (id != -1) {
            blockData.registerRender(id, aRenderBlock)
        }else{
            println("Block +\""+block.name + "\" not register")
        }
    }

    def getBlockRender(block: Block): ARenderBlock = blockData.getRender(getIdByBlock(block))

   // def getItemRender(item: Item): ARenderItem = itemData.getRender(getIdByItem(item))



}
