package ru.megains.tartess.common.network.packet.play.server


import io.netty.buffer.{ByteBufInputStream, ByteBufOutputStream}
import ru.megains.nbt.NBTData
import ru.megains.tartess.common.block.data.{BlockCell, BlockDirection, BlockPos, BlockState}
import ru.megains.tartess.common.network.handler.INetHandlerPlayClient
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}
import ru.megains.tartess.common.register.{Blocks, GameRegister}
import ru.megains.tartess.common.tileentity.TileEntity
import ru.megains.tartess.common.world.chunk.Chunk
import ru.megains.tartess.common.world.chunk.data.{BlockStorage, ChunkPosition}


class SPacketChunkData extends Packet[INetHandlerPlayClient] {



    var blockStorage: BlockStorage = _
    var chunkVoid: Boolean = false
    var position: ChunkPosition =_
    var tileEntityMap:Array[TileEntity] = _

    def this(chunkIn: Chunk) {
        this()
        blockStorage = chunkIn.blockStorage
        chunkVoid = chunkIn.isVoid
        position = chunkIn.position
        tileEntityMap = chunkIn.chunkTileEntityMap.values.toArray

    }

    override def readPacketData(buf: PacketBuffer): Unit = {
        position = new ChunkPosition(buf.readInt(),buf.readInt(),buf.readInt())

        chunkVoid = buf.readBoolean()
        blockStorage = if (chunkVoid) new BlockStorage(position) else readChunk(buf)
    }

    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(position.x)
        buf.writeInt(position.y)
        buf.writeInt(position.z)
        buf.writeBoolean(chunkVoid)
        if (!chunkVoid) writeChunk(buf)
    }

    override def processPacket(handler: INetHandlerPlayClient): Unit = {
        handler.handleChunkData(this)
    }

    def readChunk(buf: PacketBuffer): BlockStorage = {
        val blockStorage: BlockStorage = new BlockStorage(position)
        val blocksId = blockStorage.blocksId

       // val blocksHp = blockStorage.blocksHp
       // val multiBlockStorage = blockStorage.multiBlockStorage
        for (i <- 0 until 4096) {
            blocksId(i) = buf.readShort()
           // blocksHp(i) = buf.readInt()
        }

//        val sizeMultiBlock = buf.readInt()
//        for (_ <- 0 until sizeMultiBlock) {
//
//            val posMultiBlock = buf.readInt()
//            val multiBlock = new MultiBlock()
//            val blockData = multiBlock.blockData
//
//            val sizeBlock = buf.readInt()
//            for (_ <- 0 until sizeBlock) {
//
//                val posBlock = buf.readInt()
//                val blockId = buf.readInt()
//                val blockHp = buf.readInt()
//                blockData += MultiBlockPos.getForIndex(posBlock) -> (Blocks.getBlockById(blockId), blockHp)
//            }
//            multiBlockStorage.put(posMultiBlock, multiBlock)
//        }

        val sizeBlocks = buf.readInt()


        for (_ <- 0 until sizeBlocks) {
            val index = buf.readInt()
            val blockSell = new BlockCell(position)
            blockStorage.containers += index.toShort -> blockSell

            //val blockSellNBT = blockSellsNBT.getCompound(i)
            //val blocks = blockSellNBT.getInt("blocks")
           // val blockStatesNBT = blockSellNBT.getList("blockStates")

            val blocks = buf.readInt()
            for(_ <- 0 until blocks){

               // val blockStateNBT = blockStatesNBT.getCompound(i)
                val id = buf.readInt()
                val side = buf.readInt()
                val x = buf.readInt()
                val y = buf.readInt()
                val z = buf.readInt()
                val blockState = new BlockState(Blocks.getBlockById(id),new BlockPos(x,y,z),BlockDirection.DIRECTIONAL_BY_ID(side))
                //todo val blockState = getBlockState(id,side,x,y,z)
                blockSell.blocks += blockState
            }

        }

        val sizeTileEntity = buf.readInt()
        tileEntityMap = new Array[TileEntity](sizeTileEntity)

        for (i <- tileEntityMap.indices) {
            val id = buf.readInt()
            val x = buf.readInt()
            val y = buf.readInt()
            val z = buf.readInt()
            val pos = new BlockPos(x,y,z)
            val tileEntityClass = GameRegister.getTileEntityById(id)

            if(tileEntityClass != null){
                val tileEntity:TileEntity = tileEntityClass.getConstructor(classOf[BlockPos]).newInstance(pos)
                val nbt = NBTData.createCompound()
                nbt.read(new ByteBufInputStream(buf))
                tileEntity.readFromNBT(nbt)
                tileEntityMap(i) = tileEntity
            }else{
                println(s"error load tileEntity $id")
            }


        }



        blockStorage
    }

    def writeChunk(buf: PacketBuffer): Unit = {

        val blocksId = blockStorage.blocksId
      //  val blockHp = blockStorage.blocksHp
        for (i <- 0 until 4096) {
            buf.writeShort(blocksId(i))
           // buf.writeInt(blockHp(i))
        }


//        val multiBlockStorage = blockStorage.multiBlockStorage
//
//        //Колличество мультиБлоков
//        buf.writeInt(multiBlockStorage.size)
//
//
//        multiBlockStorage.foreach(
//            (data: (Int, MultiBlock)) => {
//                //Координаты мультиБлока
//                buf.writeInt(data._1)
//
//                val blockData = data._2.blockData
//                //Колличество блоков в мультиБлоке
//                buf.writeInt(blockData.size)
//
//                blockData.foreach(
//                    (data2: (MultiBlockPos, (Block, Int))) => {
//                        //Координаты блока
//                        buf.writeInt(data2._1.getIndex)
//                        //Id блока
//                        buf.writeInt(Blocks.getIdByBlock(data2._2._1))
//                        //Hp блока
//                        buf.writeInt(data2._2._2)
//                    }
//                )
//            }
//        )
        val containers = blockStorage.containers
        //Колличество
        buf.writeInt(containers.size)

        containers.foreach {
            case (index, blockSell) =>
                //Координаты
                buf.writeInt(index)

               // posList.setValue(index)
                //Колличество блоков в мультиБлоке
                buf.writeInt(blockSell.blocks.size)
                //val blockSellNBT = blockSellsNBT.createCompound()

                //blockSellNBT.setValue("blocks", blockSell.blocks.size)

              //  val blockStatesNBT = blockSellNBT.createList("blockStates", EnumNBTCompound)
                blockSell.blocks.foreach {
                    blockState =>
                        buf.writeInt(Blocks.getIdByBlock(blockState.block))
                        buf.writeInt(blockState.blockDirection.id)
                        buf.writeInt(blockState.pos.x)
                        buf.writeInt( blockState.pos.y)
                        buf.writeInt(blockState.pos.z)

                       // val blockStateNBT = blockStatesNBT.createCompound()
                       // blockStateNBT.setValue("id", Blocks.getIdByBlock(blockState.block))
                       // blockStateNBT.setValue("side", blockState.blockDirection.id)
                       // blockStateNBT.setValue("x", blockState.pos.x)
                       // blockStateNBT.setValue("y", blockState.pos.y)
                       // blockStateNBT.setValue("z", blockState.pos.z)
                }
        }

        buf.writeInt(tileEntityMap.length)

        for (tileEntity <- tileEntityMap) {

            buf.writeInt(GameRegister.getIdByTileEntity(tileEntity.getClass ))
            buf.writeInt(tileEntity.pos.x)
            buf.writeInt(tileEntity.pos.y)
            buf.writeInt(tileEntity.pos.z)

            val nbt = NBTData.createCompound()
            tileEntity.writeToNBT(nbt)
            nbt.write(new ByteBufOutputStream(buf))

        }

    }

}
