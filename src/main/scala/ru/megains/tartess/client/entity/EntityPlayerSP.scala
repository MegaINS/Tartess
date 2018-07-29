package ru.megains.tartess.client.entity

import ru.megains.tartess.client.Tartess
import ru.megains.tartess.client.network.handler.NetHandlerPlayClient
import ru.megains.tartess.common.block.data.BlockPos
import ru.megains.tartess.common.entity.player.EntityPlayer
import ru.megains.tartess.common.network.packet.play.client.CPacketPlayer
import ru.megains.tartess.common.physics.AABB
import ru.megains.tartess.common.tileentity.ATileEntityInventory
import ru.megains.tartess.common.world.World

class EntityPlayerSP(oc: Tartess, world: World, val connection: NetHandlerPlayClient) extends EntityPlayer(" ") {


    private var lastReportedPosX: Double = .0
    private var lastReportedPosY: Double = .0
    private var lastReportedPosZ: Double = .0
    private var lastReportedYaw: Double = .0
    private var lastReportedPitch: Double = .0
    private var positionUpdateTicks: Int = 0
    private var prevOnGround: Boolean = false




    override def openGui(world: World, pos: BlockPos): Unit = {
        val tileEntity = world.getTileEntity(pos)
        tileEntity match {
            case inv:ATileEntityInventory =>
                val gui = inv.getGui(this)
                openContainer = gui.inventorySlots
                Tartess.tartess.guiManager.setGuiScreen(gui)
            case _=>
        }

    }

    override def update() {
        // if (worldObj.isBlockLoaded(new BlockPos(posX, 0.0D, posZ))) {
        super.update()
        //            if (this.isRiding) {
        //                this.connection.sendPacket(new CPacketPlayer.Rotation(this.rotationYaw, this.rotationPitch, this.onGround))
        //                this.connection.sendPacket(new CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak))
        //                val entity: Entity = this.getLowestRidingEntity
        //                if ((entity ne this) && entity.canPassengerSteer) this.connection.sendPacket(new CPacketVehicleMove(entity))
        //            }
        //            else
        onUpdateWalkingPlayer()
        //  }
    }


    def onUpdateWalkingPlayer() {
        // val flag: Boolean =  isSprinting
        //        if (flag !=  serverSprintState) {
        //            if (flag)  connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.START_SPRINTING))
        //            else  connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.STOP_SPRINTING))
        //             serverSprintState = flag
        //        }
        // val flag1: Boolean =  isSneaking
        //        if (flag1 !=  serverSneakState) {
        //            if (flag1)  connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.START_SNEAKING))
        //            else  connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.STOP_SNEAKING))
        //             serverSneakState = flag1
        //        }
        if (isCurrentViewEntity) {
            val axisalignedbb: AABB = body
            val d0: Double = posX - lastReportedPosX
            val d1: Double = axisalignedbb.minY - lastReportedPosY
            val d2: Double = posZ - lastReportedPosZ
            val d3: Double = rotationYaw - lastReportedYaw
            val d4: Double = rotationPitch - lastReportedPitch
            positionUpdateTicks += 1
            val flag2: Boolean = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || positionUpdateTicks >= 20
            val flag3: Boolean = d3 != 0.0D || d4 != 0.0D
            //            if ( isRiding) {
            //                 connection.sendPacket(new CPacketPlayer.PositionRotation( motionX, -999.0D,  motionZ,  rotationYaw,  rotationPitch,  onGround))
            //                flag2 = false
            //            }
            //   else

            if (flag2 && flag3) connection.sendPacket(new CPacketPlayer.PositionRotation(posX, axisalignedbb.minY, posZ, rotationYaw, rotationPitch, onGround))
            else if (flag2) connection.sendPacket(new CPacketPlayer.Position(posX, axisalignedbb.minY, posZ, onGround))
            else if (flag3) connection.sendPacket(new CPacketPlayer.Rotation(rotationYaw, rotationPitch, onGround))
            else if (prevOnGround != onGround) connection.sendPacket(new CPacketPlayer(onGround))
            if (flag2) {
                lastReportedPosX = posX
                lastReportedPosY = axisalignedbb.minY
                lastReportedPosZ = posZ
                positionUpdateTicks = 0
            }
            if (flag3) {
                lastReportedYaw = rotationYaw
                lastReportedPitch = rotationPitch
            }
            prevOnGround = onGround
            //   field_189811_cr =  mc.gameSettings.field_189989_R
        }
    }

    protected def isCurrentViewEntity: Boolean = oc.renderViewEntity == this
}
