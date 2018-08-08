package ru.megains.tartess.server.entity

import ru.megains.tartess.common.block.data.BlockPos
import ru.megains.tartess.common.container.Container
import ru.megains.tartess.common.entity.player.{EntityPlayer, GameType}
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.common.network.packet.play.server.{SPacketChangeGameState, SPacketSetSlot, SPacketWindowItems}
import ru.megains.tartess.common.tileentity.ATileEntityInventory
import ru.megains.tartess.common.world.World
import ru.megains.tartess.server.PlayerInteractionManager
import ru.megains.tartess.server.network.handler.NetHandlerPlayServer
import ru.megains.tartess.server.world.WorldServer

class EntityPlayerMP(name: String, val interactionManager: PlayerInteractionManager) extends EntityPlayer(name: String) {


    //val ocServer = getWorldServer.server
    interactionManager.thisPlayerMP = this
    var connection: NetHandlerPlayServer = _
    var managedPosZ: Double = .0
    var managedPosY: Double = .0
    var managedPosX: Double = .0

    var playerLastActiveTime: Long = 0

    def setGameType(gameType: GameType) {
        interactionManager.setGameType(gameType)
        this.connection.sendPacket(new SPacketChangeGameState(3, gameType.id))

    }


    def getWorldServer: WorldServer = world.asInstanceOf[WorldServer]


//    def markPlayerActive() {
//        playerLastActiveTime = TartessServer.getCurrentTimeMillis
//    }

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
        openContainer.detectAndSendChanges()
    }

    def closeContainer(): Unit = {
        //this.openContainer.onContainerClosed(this)
        this.openContainer = this.inventoryContainer
    }
    override def openGui(world: World, pos: BlockPos): Unit = {
        val tileEntity = world.getTileEntity(pos)
        tileEntity match {
            case inv:ATileEntityInventory =>
                openContainer = inv.getContainer(this)
                openContainer.addListener(this)
            case _=>
        }

    }
}
