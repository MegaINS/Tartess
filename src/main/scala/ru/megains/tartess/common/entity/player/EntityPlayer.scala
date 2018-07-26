package ru.megains.tartess.common.entity.player

import ru.megains.nbt.tag.NBTCompound
import ru.megains.tartess.client.Tartess
import ru.megains.tartess.common.block.data.{BlockDirection, BlockPos}
import ru.megains.tartess.common.container.{Container, ContainerPlayerInventory}
import ru.megains.tartess.common.entity.EntityLivingBase
import ru.megains.tartess.common.inventory.InventoryPlayer
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.client.renderer.gui.GuiPlayerInventory
import ru.megains.tartess.common.tileentity.ATileEntityInventory
import ru.megains.tartess.common.world.World

class EntityPlayer(val name:String) extends EntityLivingBase(1.8f*16, 0.6f*16, 1.6f*16) {


    var openContainer: Container = _
    val inventory = new InventoryPlayer(this)
    val inventoryContainer: Container = new ContainerPlayerInventory(inventory)
    var gameType:GameType = GameType.SURVIVAL

    def turn(xo: Float, yo: Float) {
        yRot += yo * 0.15f
        xRot += xo * 0.15f
        if (xRot < -90.0F) {
            xRot = -90.0F
        }
        if (xRot > 90.0F) {
            xRot = 90.0F
        }
        if (yRot > 360.0F) {
            yRot -= 360.0F
        }
        if (yRot < 0.0F) {
            yRot += 360.0F
        }
    }

    def openGui(world: World, pos: BlockPos): Unit = {
        val tileEntity = world.getTileEntity(pos)
        tileEntity match {
            case inv:ATileEntityInventory =>
                val gui = inv.getGui(this)
                openContainer = gui.inventorySlots
                Tartess.tartess.guiManager.setGuiScreen(gui)
            case _=>
        }

    }

    def update(xo: Float, yo: Float, zo: Float) {


        if(gameType.isCreative) {
            motionY = yo / 2 * 16
        }else{
            if (yo > 0 && onGround) {
                motionY = 0.42f*16
            }
        }



        moveFlying(xo, zo, if (onGround || gameType.isCreative) 0.04f else 0.02f)
        move(motionX, motionY, motionZ)
        motionX *= 0.8f
        if (motionY > 0.0f) {
            motionY *= 0.90f
        }
        else {
            motionY *= 0.98f
        }
        motionZ *= 0.8f
        motionY -= 0.04f*16
        if (onGround && gameType.isSurvival) {
            motionX *= 0.9f
            motionZ *= 0.9f
        }
    }

    override def update(): Unit = {
        yRot match {
            case y if y > 315 || y <45 => side = BlockDirection.NORTH
            case y if y <135 => side = BlockDirection.EAST
            case y if y <225 => side = BlockDirection.SOUTH
            case y if y <315 => side = BlockDirection.WEST
            case _ => side = BlockDirection.UP
        }

    }

    def setHeldItem(stack: ItemPack): Unit = setItemStackToSlot(stack)

    def openInventory(): Unit = {
        openContainer = inventoryContainer
        Tartess.tartess.guiManager.setGuiScreen(new GuiPlayerInventory(this))
    }

    def getHeldItem: ItemPack = getItemStackFromSlot

    def getItemStackFromSlot: ItemPack = inventory.getStackSelect

    def setItemStackToSlot(stack: ItemPack) {

        inventory.mainInventory(inventory.stackSelect) = stack
    }

    override def readEntityFromNBT(compound: NBTCompound): Unit = {
        inventory.readFromNBT(compound)
    }

    override def writeEntityToNBT(compound: NBTCompound): Unit = {
        inventory.writeToNBT(compound)
    }
}