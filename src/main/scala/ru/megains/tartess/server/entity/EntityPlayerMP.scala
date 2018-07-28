package ru.megains.tartess.server.entity

import ru.megains.tartess.common.container.Container
import ru.megains.tartess.common.entity.player.{EntityPlayer, GameType}
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.common.network.packet.play.server.{SPacketChangeGameState, SPacketSetSlot, SPacketWindowItems}
import ru.megains.tartess.common.world.World
import ru.megains.tartess.server.network.handler.NetHandlerPlayServer
import ru.megains.tartess.server.world.WorldServer
import ru.megains.tartess.server.{PlayerInteractionManager, TartessServer}

class EntityPlayerMP(name: String, world: World, val interactionManager: PlayerInteractionManager) extends EntityPlayer(name: String) {
    setWorld(world)

    val ocServer = getWorldServer.server
    interactionManager.thisPlayerMP = this
    var connection: NetHandlerPlayServer = _
    var managedPosZ: Double = .0
    var managedPosY: Double = .0
    var managedPosX: Double = .0

    var playerLastActiveTime: Long = 0

    def setGameType(gameType: GameType) {
        interactionManager.setGameType(gameType)
        this.connection.sendPacket(new SPacketChangeGameState(3, gameType.id))
        //    if (gameType eq GameType.SPECTATOR) this.dismountRidingEntity()
        //  else this.setSpectatingEntity(this)
        //  this.sendPlayerAbilities()
        //  this.markPotionsDirty()
    }


    def getWorldServer: WorldServer = world.asInstanceOf[WorldServer]


    def markPlayerActive() {
        playerLastActiveTime = TartessServer.getCurrentTimeMillis
    }

    def addSelfToInternalCraftingInventory() {
        openContainer.addListener(this)
    }

    def sendSlotContents(containerToSend: Container, slotInd: Int, stack: ItemPack) {
        connection.sendPacket(new SPacketSetSlot(-1, slotInd, stack))
    }

    def updateCraftingInventory(containerToSend: Container, itemsList: Array[ItemPack]) {
        connection.sendPacket(new SPacketWindowItems(-1, itemsList))
        updateHeldItem()
    }

    def updateHeldItem() {
        connection.sendPacket(new SPacketSetSlot(-1, -1, inventory.itemStack))
    }

    override def update(): Unit = {
        super.update()
       //todo openContainer.detectAndSendChanges()
    }
}
