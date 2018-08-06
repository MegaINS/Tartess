package ru.megains.tartess.server

import ru.megains.nbt.tag.NBTCompound
import ru.megains.tartess.common.block.data.BlockPos
import ru.megains.tartess.common.entity.player.EntityPlayer
import ru.megains.tartess.common.network.NetworkManager
import ru.megains.tartess.common.network.handler.INetHandler
import ru.megains.tartess.common.network.packet.Packet
import ru.megains.tartess.common.network.packet.play.server.{SPacketHeldItemChange, SPacketJoinGame, SPacketSpawnPosition}
import ru.megains.tartess.common.world.data.AnvilSaveHandler
import ru.megains.tartess.server.entity.EntityPlayerMP
import ru.megains.tartess.server.network.handler.NetHandlerPlayServer
import ru.megains.tartess.server.world.WorldServer

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class PlayerList(server: TartessServer) {
    var playerNBTManager: AnvilSaveHandler = server.world.saveHandler
    val playerEntityList: ArrayBuffer[EntityPlayerMP] = new ArrayBuffer[EntityPlayerMP]()


    val idToPlayerMap: mutable.HashMap[Int, EntityPlayerMP] = new mutable.HashMap[Int, EntityPlayerMP]()
    val nameToPlayerMap: mutable.HashMap[String, EntityPlayerMP] = new mutable.HashMap[String, EntityPlayerMP]()


    def createPlayerForUser(name: String): EntityPlayerMP = {
        val playerinteractionmanager: PlayerInteractionManager = new PlayerInteractionManager(server.world)
        new EntityPlayerMP(name, playerinteractionmanager)
    }


    def initializeConnectionToPlayer(netManager: NetworkManager, playerIn: EntityPlayerMP) {


        val nethandlerplayserver: NetHandlerPlayServer = new NetHandlerPlayServer(server, netManager, playerIn)



        val nbttagcompound: NBTCompound = readPlayerDataFromFile(playerIn)

        playerIn.setWorld(server.world)
        val worldserver: WorldServer = server.world
        val blockpos: BlockPos = worldserver.spawnPoint



        playerIn.connection = nethandlerplayserver
        nethandlerplayserver.sendPacket(new SPacketJoinGame())
        nethandlerplayserver.sendPacket(new SPacketSpawnPosition(blockpos))
        nethandlerplayserver.sendPacket(new SPacketHeldItemChange(playerIn.inventory.stackSelect))


        playerLoggedIn(playerIn)
        nethandlerplayserver.setPlayerLocation(playerIn.posX, playerIn.posY, playerIn.posZ, playerIn.rotationYaw, playerIn.rotationPitch)
        playerIn.addSelfToInternalCraftingInventory()
    }

    def readPlayerDataFromFile(playerIn: EntityPlayerMP): NBTCompound = {

        var nbttagcompound1: NBTCompound = null
        nbttagcompound1 = playerNBTManager.readPlayerData(playerIn)
        nbttagcompound1
    }

    def playerLoggedIn(playerIn: EntityPlayerMP) {
        playerEntityList += playerIn
        nameToPlayerMap += playerIn.name -> playerIn
        //sendPacketToAllPlayers(new SPacketPlayerListItem(SPacketPlayerListItem.Action.ADD_PLAYER, Array[EntityPlayerMP](playerIn)))
        val worldserver: WorldServer = server.world


        worldserver.spawnEntityInWorld(playerIn)
        preparePlayer(playerIn, null)
    }

    def preparePlayer(playerIn: EntityPlayerMP, worldIn: WorldServer) {
        val worldserver: WorldServer = playerIn.getWorldServer
        if (worldIn != null) worldIn.playerManager.removePlayer(playerIn)
        worldserver.playerManager.addPlayer(playerIn)
        worldserver.chunkProvider.provideChunk(playerIn.posX.toInt >> 8, playerIn.posY.toInt >> 8, playerIn.posZ.toInt >> 8)
    }

    def writePlayerData(playerIn: EntityPlayerMP): Unit = {
          if (playerIn.connection == null) return
        playerNBTManager.writePlayerData(playerIn)
    }

    def playerLoggedOut(playerIn: EntityPlayerMP) {

        val worldserver: WorldServer = playerIn.getWorldServer

        writePlayerData(playerIn)
        worldserver.removeEntity(playerIn)
        worldserver.playerManager.removePlayer(playerIn)
        playerEntityList -= playerIn

        val player = nameToPlayerMap.getOrElse(playerIn.name,null)
        if (playerIn == player) {
            nameToPlayerMap -= playerIn.name
        }
    }

    def serverUpdateMountedMovingPlayer(player: EntityPlayerMP) {
          player.getWorldServer.playerManager.updateMountedMovingPlayer(player)
    }

    def sendPacketToAllPlayers(packetIn: Packet[_ <: INetHandler]) {
         playerEntityList.foreach(_.connection.sendPacket(packetIn))
    }

    def getPlayerByID(playerID: Int): EntityPlayer = idToPlayerMap.getOrElse(playerID, null)

    def getPlayerByName(player: String): EntityPlayerMP = nameToPlayerMap.getOrElse(player, null)


}
