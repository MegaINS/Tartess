package ru.megains.tartess.server.network.handler

import ru.megains.tartess.client.module.NEI.CPacketNEI
import ru.megains.tartess.common.block.data.BlockPos
import ru.megains.tartess.common.inventory.InventoryPlayer
import ru.megains.tartess.common.network.NetworkManager
import ru.megains.tartess.common.network.handler.{INetHandler, INetHandlerPlayServer}
import ru.megains.tartess.common.network.packet.Packet
import ru.megains.tartess.common.network.packet.play.client._
import ru.megains.tartess.common.network.packet.play.server.SPacketPlayerPosLook
import ru.megains.tartess.common.utils.{Logger, RayTraceType, Vec3f}
import ru.megains.tartess.server.TartessServer
import ru.megains.tartess.server.entity.EntityPlayerMP

import scala.collection.immutable.HashSet

class NetHandlerPlayServer(server: TartessServer, val networkManager: NetworkManager, playerEntity: EntityPlayerMP) extends INetHandlerPlayServer with Logger[NetHandlerPlayServer] {

    networkManager.setNetHandler(this)

    playerEntity.connection = this


    var targetPos: Vec3f = _
    private var lastGoodX: Float = .0f
    private var lastGoodY: Float = .0f
    private var lastGoodZ: Float = .0f
    private var firstGoodX: Float = .0f
    private var firstGoodY: Float = .0f
    private var firstGoodZ: Float = .0f

    override def sendPacket(packetIn: Packet[_ <: INetHandler]) {

        try
            networkManager.sendPacket(packetIn)

        catch {
            case throwable: Throwable =>
                log.error("sendPacket", throwable)
        }
    }

    def setPlayerLocation(x: Float, y: Float, z: Float, yaw: Float, pitch: Float) {
        setPlayerLocation(x, y, z, yaw, pitch, new HashSet[SPacketPlayerPosLook.EnumFlags.EnumFlags])
    }

    var teleportId: Int = 0

    def processNEI(packetIn: CPacketNEI): Unit ={
        playerEntity.inventory.addItemStackToInventory(packetIn.itemPack)
    }

    def setPlayerLocation(x: Float, y: Float, z: Float, yaw: Float, pitch: Float, relativeSet: HashSet[SPacketPlayerPosLook.EnumFlags.EnumFlags]) {


        val d0: Float = if (relativeSet.contains(SPacketPlayerPosLook.EnumFlags.X)) playerEntity.posX
        else 0.0f
        val d1: Float = if (relativeSet.contains(SPacketPlayerPosLook.EnumFlags.Y)) playerEntity.posY
        else 0.0f
        val d2: Float = if (relativeSet.contains(SPacketPlayerPosLook.EnumFlags.Z)) playerEntity.posZ
        else 0.0f
        targetPos = new Vec3f(x + d0, y + d1, z + d2)
        var f: Float = yaw
        var f1: Float = pitch
        if (relativeSet.contains(SPacketPlayerPosLook.EnumFlags.Y_ROT)) f = yaw + playerEntity.rotationYaw
        if (relativeSet.contains(SPacketPlayerPosLook.EnumFlags.X_ROT)) f1 = pitch + playerEntity.rotationPitch
        if ({teleportId += 1; teleportId} == Integer.MAX_VALUE) teleportId = 0
        playerEntity.setPositionAndRotation(targetPos.x, targetPos.y, targetPos.z, f, f1)
        playerEntity.connection.sendPacket(new SPacketPlayerPosLook(x, y, z, yaw, pitch, relativeSet, teleportId))
    }


    override def onDisconnect(msg: String): Unit = {

        log.info("{} lost connection: {}", Array[AnyRef](playerEntity.name, msg))
        server.playerList.playerLoggedOut(playerEntity)

    }

    override def processPlayer(packetIn: CPacketPlayer): Unit = {


        val d4: Float = packetIn.getX(playerEntity.posX)
        val d5: Float = packetIn.getY(playerEntity.posY)
        val d6: Float = packetIn.getZ(playerEntity.posZ)
        val f: Float = packetIn.getYaw(playerEntity.rotationYaw)
        val f1: Float = packetIn.getPitch(playerEntity.rotationPitch)
        var d7: Float = d4 - firstGoodX
        var d8: Float = d5 - firstGoodY
        var d9: Float = d6 - firstGoodZ

        d7 = d4 - lastGoodX - playerEntity.posX
        d8 = d5 - lastGoodY - playerEntity.posY
        d9 = d6 - lastGoodZ - playerEntity.posZ

        playerEntity.move(d7, d8, d9)

        d7 = d4 - playerEntity.posX
        d8 = d5 - playerEntity.posY
        if (d8 > -0.5D || d8 < 0.5D) d8 = 0.0f
        d9 = d6 - playerEntity.posZ

        playerEntity.setPositionAndRotation(d4, d5, d6, f, f1)

        server.playerList.serverUpdateMountedMovingPlayer(playerEntity)

       // lastGoodX = playerEntity.posX
       // lastGoodY = playerEntity.posY
       // lastGoodZ = playerEntity.posZ

    }


    override def processHeldItemChange(packetIn: CPacketHeldItemChange) {
        if (packetIn.slotId >= 0 && packetIn.slotId < InventoryPlayer.hotBarSize) {
            playerEntity.inventory.stackSelect = packetIn.slotId

        }
        else log.warn("{} tried to set an invalid carried item", Array[AnyRef](playerEntity.name))
    }



    override def processClickWindow(packetIn: CPacketClickWindow): Unit = {
        playerEntity.openContainer.mouseClicked(packetIn.mouseX, packetIn.mouseY, packetIn.button, playerEntity)
        playerEntity.updateHeldItem()
    }

    override def disconnect(msg: String): Unit = {

    }

    override def processPlayerMouse(packetIn: CPacketPlayerMouse): Unit = {
        packetIn.button match {
            case 0 =>



              packetIn.rayTraceResult.rayTraceType match {
                    case RayTraceType.BLOCK  =>
                        val blockPos: BlockPos = packetIn.rayTraceResult.blockPos

                        val d0: Double = playerEntity.posX - (blockPos.x.toDouble + 0.5D)
                        val d1: Double = playerEntity.posY - (blockPos.y.toDouble + 0.5D) + 1.5D
                        val d2: Double = playerEntity.posZ - (blockPos.z.toDouble + 0.5D)
                        val d3: Double = d0 * d0 + d1 * d1 + d2 * d2
                        var dist: Double = playerEntity.interactionManager.getBlockReachDistance + 1
                        dist *= dist
                        if (d3 > dist) {

                        } else {
                            playerEntity.interactionManager.onBlockClicked(blockPos, packetIn.rayTraceResult.sideHit)
                        }
                    case RayTraceType.VOID  =>
                    case RayTraceType.ENTITY  =>
                }

            case 1 =>


                packetIn.rayTraceResult.rayTraceType match {
                    case RayTraceType.VOID  =>
                    case RayTraceType.BLOCK  =>

                       playerEntity.interactionManager.processRightClickBlock(packetIn.rayTraceResult,packetIn.blockState)

                    case RayTraceType.ENTITY  =>
                }
            case _=> log.info(s"${playerEntity.name} click mouse button ${packetIn.button}")
        }
    }

    override def processCloseWindow(packetIn: CPacketCloseWindow): Unit = {
        playerEntity.closeContainer()
    }
}
