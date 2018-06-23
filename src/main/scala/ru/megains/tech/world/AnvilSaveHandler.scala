package ru.megains.tech.world

import ru.megains.nbt.tag.NBTCompound
import ru.megains.old.entity.player.EntityPlayer
import ru.megains.tartess.utils.Logger
import ru.megains.tech.world.storage.ChunkLoader

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
           // compound = NBTData.readOfFile(playersDirectory, player.name).getCompound
        } catch {
            case _: Exception =>
             //   log.warn("Failed to load player data for {}", Array[AnyRef](player.name))
        }
        if (compound != null) {
          // player.readFromNBT(compound)
        }
        else {
//            val inventory = player.inventory
//
//
//            val rand: Int = Random.nextInt(15) + 1
//            for (id <- 1 to rand) {
//                inventory.addItemStackToInventory(new ItemStack(Blocks.getBlockById(2 + Random.nextInt(10)), id))
//            }
        }

        compound
    }

//    def writePlayerData(playerIn: EntityPlayerMP): Unit = {
//        val compound: NBTCompound = NBTData.createCompound()
//        playerIn.writeToNBT(compound)
//        NBTData.writeToFile(compound, playersDirectory, playerIn.name)
//
//    }


}
