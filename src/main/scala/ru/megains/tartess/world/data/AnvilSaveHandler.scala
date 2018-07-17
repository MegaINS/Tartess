package ru.megains.tartess.world.data

import ru.megains.nbt.NBTData
import ru.megains.nbt.tag.NBTCompound
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.item.itemstack.ItemPack
import ru.megains.tartess.register.GameRegister
import ru.megains.tartess.utils.Logger
import ru.megains.tartess.world.chunk.data.ChunkLoader

import scala.reflect.io.{Directory, Path}

class AnvilSaveHandler(savesDirectory: Directory, worldName: String)  extends Logger[AnvilSaveHandler] {


    def flush(): Unit = {}


    val worldDirectory: Directory = savesDirectory / Path(worldName).toDirectory
    val playersDirectory: Directory = worldDirectory / Path("playerData").toDirectory
    val chunkLoader: ChunkLoader = new ChunkLoader(worldDirectory)

    def getChunkLoader: ChunkLoader = {
        chunkLoader
    }


    def readPlayerData(player: EntityPlayer): NBTCompound = {
        var compound: NBTCompound = null
        try {
            compound = NBTData.readOfFile(playersDirectory, player.name).getCompound
        } catch {
            case _: Exception =>
                log.warn("Failed to load player data for {}", Array[AnyRef](player.name))
        }
        if (compound != null && compound.nbtMap.nonEmpty ) {
           player.readFromNBT(compound)
        }
        else {
            val inventory = player.inventory

                GameRegister.getItems.foreach(item=>{
                    inventory.addItemStackToInventory(new ItemPack(item,5))
                    inventory.addItemStackToInventory(new ItemPack(item,5))
                })

        }

        compound
    }

    def writePlayerData(playerIn: EntityPlayer): Unit = {
        val compound: NBTCompound = NBTData.createCompound()
        playerIn.writeToNBT(compound)
        NBTData.writeToFile(compound, playersDirectory, playerIn.name)

    }


}
