package ru.megains.tartess.common.world.chunk.data

import ru.megains.nbt.NBTData
import ru.megains.nbt.tag.NBTCompound
import ru.megains.nbt.NBTType._
import ru.megains.tartess.common.block.data.{BlockCell, BlockDirection, BlockPos, BlockState}
import ru.megains.tartess.common.entity.Entity
import ru.megains.tartess.common.entity.player.EntityPlayer
import ru.megains.tartess.common.register.{Blocks, GameRegister}
import ru.megains.tartess.common.world.World
import ru.megains.tartess.common.world.chunk.{Chunk, ChunkVoid}
import ru.megains.tartess.common.world.region.RegionLoader

import scala.collection.mutable
import scala.language.postfixOps
import scala.reflect.io.Directory


class ChunkLoader(worldDirectory: Directory) {


    val regionLoader = new RegionLoader(worldDirectory)
    val blockStates = new mutable.HashMap[Long,BlockState]()
    def getBlockState(id: Int, side: Int, x: Int, y: Int, z: Int):BlockState = {
        blockStates.getOrElseUpdate(Chunk.getIndex(x,y,z),new BlockState(Blocks.getBlockById(id),new BlockPos(x,y,z),BlockDirection.DIRECTIONAL_BY_ID(side)))
    }

    def saveChunk(chunk: Chunk): Unit = {
        if (!chunk.isInstanceOf[ChunkVoid] /*&& chunk.isSaved*/) {
            val chunkNBT = NBTData.createCompound()

            writeChunk(chunkNBT, chunk)
            val pos = chunk.position
            val output = regionLoader.getChunkOutputStream(pos.x, pos.y, pos.z)
            NBTData.writeToStream(chunkNBT, output)
            output.close()
        }
    }


    def loadChunk(world: World, x: Int, y: Int, z: Int): Chunk = {
        var chunk: Chunk = null
        val input = regionLoader.getChunkInputStream(x, y, z)
        if (input != null) {
            val nbt = NBTData.readOfStream(input)
            if (nbt.isCompound) {
                try {
                    chunk = new Chunk(new ChunkPosition(x, y, z),world)
                    readChunk(chunk,nbt.getCompound)
                }
                catch {
                    case var3: Exception =>
                        println("Error load chunk")
                        var3.printStackTrace()
                }
            }
        }
        chunk
    }
    def readChunk(chunk: Chunk, compound: NBTCompound): Unit = {


        val blockStorage: BlockStorage = chunk.blockStorage
        blockStorage.blocksId = compound.getArrayShort("blocksId")

        val containers = compound.getCompound("containers")
        val posList = containers.getList("position")
        val blockSellsNBT =  containers.getList("blockSell")

        for (i <- 0 until posList.getLength) {
            val index = posList.getShort(i)
            val blockSell = new BlockCell(chunk.position)
            blockStorage.containers += index -> blockSell
            val blockSellNBT = blockSellsNBT.getCompound(i)
            val blocks = blockSellNBT.getInt("blocks")
            val blockStatesNBT = blockSellNBT.getList("blockStates")


            for(i <- 0 until blocks){
                    val blockStateNBT = blockStatesNBT.getCompound(i)
                    val id = blockStateNBT.getInt("id")
                    val side = blockStateNBT.getInt("side")
                    val x = blockStateNBT.getInt("x")
                    val y = blockStateNBT.getInt("y")
                    val z = blockStateNBT.getInt("z")
                    val blockState = getBlockState(id,side,x,y,z)

                    blockSell.blocks += blockState
                }

        }

        val tileEntitysNBT =  containers.getList("tileEntities")

        for (i <- 0 until tileEntitysNBT.getLength) {
            val tileEntityNBT = tileEntitysNBT.getCompound(i)
            val x = tileEntityNBT.getInt("x")
            val y = tileEntityNBT.getInt("y")
            val z = tileEntityNBT.getInt("z")
            val id = tileEntityNBT.getInt("id")
            val pos = new BlockPos(x,y,z)
            val tileEntityClass = GameRegister.getTileEntityById(id)

            if(tileEntityClass != null){
                val tileEntity = tileEntityClass.getConstructor(classOf[BlockPos]).newInstance(pos)
                tileEntity.readFromNBT(tileEntityNBT)
                chunk.addTileEntity(tileEntity)
            }else{
                println(s"error load tileEntity $id")
            }


        }
        val entitiesNBT =  containers.getList("entities")

        for (i <- 0 until entitiesNBT.getLength) {
            val entityNBT = entitiesNBT.getCompound(i)
            val id = entityNBT.getInt("id")
            val entityClass = GameRegister.getEntityById(id)
            if(entityClass != null){
                val entity:Entity = entityClass.getConstructor().newInstance()
                entity.readFromNBT(entityNBT)
                chunk.addEntity(entity)
            }else{
                println(s"error load entity $id")
            }
        }
    }

    def writeChunk(compound: NBTCompound, chunk: Chunk): Unit = {

        val blockStorage: BlockStorage = chunk.blockStorage
        val containers = chunk.blockStorage.containers
        val tileEntity = chunk.chunkTileEntityMap.values
        val entityMap = chunk.chunkEntityMap
        compound.setValue("blocksId", blockStorage.blocksId)
        val containersNBT = compound.createCompound("containers")

        val posList = containersNBT.createList("position", EnumNBTShort)
        val blockSellsNBT = containersNBT.createList("blockSell", EnumNBTCompound)

        containers.foreach {
            case (index, blockSell) =>
                posList.setValue(index)
                val blockSellNBT = blockSellsNBT.createCompound()
                blockSellNBT.setValue("blocks", blockSell.blocks.size)
                val blockStatesNBT = blockSellNBT.createList("blockStates", EnumNBTCompound)
                blockSell.blocks.foreach {
                    blockState =>
                        val blockStateNBT = blockStatesNBT.createCompound()
                        blockStateNBT.setValue("id", Blocks.getIdByBlock(blockState.block))
                        blockStateNBT.setValue("side", blockState.blockDirection.id)
                        blockStateNBT.setValue("x", blockState.pos.x)
                        blockStateNBT.setValue("y", blockState.pos.y)
                        blockStateNBT.setValue("z", blockState.pos.z)
                }
        }

        val tileEntitiesNBT = containersNBT.createList("tileEntities", EnumNBTCompound)
        tileEntity.foreach{ tileEntity =>
            val tileEntityNBT = tileEntitiesNBT.createCompound()
            tileEntityNBT.setValue("id", GameRegister.getIdByTileEntity(tileEntity.getClass))
            tileEntityNBT.setValue("x", tileEntity.pos.x)
            tileEntityNBT.setValue("y", tileEntity.pos.y)
            tileEntityNBT.setValue("z", tileEntity.pos.z)
            tileEntity.writeToNBT(tileEntityNBT)
        }

        val entitiesNBT = containersNBT.createList("entities", EnumNBTCompound)
        entityMap.foreach{
            case _:EntityPlayer =>
            case entity =>
                val entityNBT: NBTCompound = entitiesNBT.createCompound()
                entityNBT.setValue("id", GameRegister.getIdByEntity(entity.getClass))
                entity.writeToNBT(entityNBT)
        }



    }

    def saveExtraChunkData(worldIn: World, chunkIn: Chunk) {
    }
}
