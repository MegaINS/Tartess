package ru.megains.tartess.client.network

import ru.megains.tartess.client.Tartess
import ru.megains.tartess.client.entity.EntityPlayerSP
import ru.megains.tartess.client.network.handler.NetHandlerPlayClient
import ru.megains.tartess.common.block.Block
import ru.megains.tartess.common.block.data.{BlockDirection, BlockPos}
import ru.megains.tartess.common.entity.player.{EntityPlayer, GameType}
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.common.network.packet.play.client._
import ru.megains.tartess.common.utils.EnumActionResult.EnumActionResult
import ru.megains.tartess.common.utils.{EnumActionResult, Vec3f}
import ru.megains.tartess.common.world.World

class PlayerControllerMP(tar:Tartess,val net: NetHandlerPlayClient) {



    var isHittingBlock: Boolean = false
    var blockHitDelay: Int = 0
    var currentPlayerItem: Int = 0
    def setGameType(gameType: GameType): Unit = {
        currentGameType = gameType
    }

    def sendQuittingDisconnectingPacket(): Unit = {
        net.netManager.closeChannel("Quitting")
    }
    def createClientPlayer(world: World): EntityPlayerSP = {
        new EntityPlayerSP(tar, world, net)
    }


    var currentGameType: GameType = GameType.CREATIVE


    def rightClickMouse(): Unit = {
        net.sendPacket(new CPacketPlayerMouse(1,0))
    }
//    def clickBlock(loc: BlockPos, face: BlockDirection): Boolean = {
//
//        if (currentGameType.isCreative) {
//            net.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, loc, face))
//
//            //todo clickBlockCreative(this, loc)
//            blockHitDelay = 5
//        }
//
//        true
//    }
    def leftClickMouse(): Unit = {
        net.sendPacket(new CPacketPlayerMouse(0,0))
    }

    def processRightClickBlock(player: EntityPlayer, worldIn: World, stack: ItemPack, pos: BlockPos, facing: BlockDirection, vec: Vec3f): EnumActionResult = {

        syncCurrentPlayItem()
        val f: Float = (vec.x - pos.x.toDouble).toFloat
        val f1: Float = (vec.y - pos.y.toDouble).toFloat
        val f2: Float = (vec.z - pos.z.toDouble).toFloat
//        var flag: Boolean = false
//
//
//
//        val item: Item = if (stack == null) null
//        else stack.item
//        val ret: EnumActionResult = if (item == null) EnumActionResult.PASS
//        else item.onItemUseFirst(stack, player, worldIn, pos, facing, f, f1, f2)
//        if (ret != EnumActionResult.PASS) return ret
        val block: Block = worldIn.getBlock(pos).block
        block.onBlockActivated(worldIn, pos, player, stack, facing, f, f1,f2)

        net.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, tar.blockSelectPosition, facing, f, f1, f2))
//        if (!flag) {
//            if (stack == null){
//                EnumActionResult.PASS
//            } else {
//                val j: Int = stack.stackSize
//                val enumactionresult: EnumActionResult = stack.onItemUse(player, worldIn, tar.blockSelectPosition, facing, f, f1, f2)
//
//                stack.stackSize = j
//                enumactionresult
//            }
//        }
//        else EnumActionResult.SUCCESS
        EnumActionResult.SUCCESS
    }

    def processRightClick(player: EntityPlayer, worldIn: World, stack: ItemPack): EnumActionResult = {
        syncCurrentPlayItem()
        net.sendPacket(new CPacketPlayerTryUseItem())
//        val i: Int = stack.stackSize
//        val actionresult: ActionResult[ItemPack] = stack.useItemRightClick(worldIn, player)
//        val itemstack: ItemPack = actionresult.result
//        if ((itemstack != stack) || itemstack.stackSize != i) {
//            player.setHeldItem(itemstack)
//            if (itemstack.stackSize <= 0) {
//                player.setHeldItem(null)
//            }
//        }
//        actionresult.`type`
        EnumActionResult.SUCCESS
    }

    private def syncCurrentPlayItem() {
        val i: Int = tar.player.inventory.stackSelect
        if (i != currentPlayerItem) {
            currentPlayerItem = i
            net.sendPacket(new CPacketHeldItemChange(currentPlayerItem))
        }
    }




//    def resetBlockRemoving() {
//
//    }


//    def onPlayerDamageBlock(posBlock: BlockPos, directionFacing: BlockDirection): Boolean = {
//        syncCurrentPlayItem()
//        if (blockHitDelay > 0) {
//            blockHitDelay -= 1
//            true
//        }
//        else if (currentGameType.isCreative /* && mc.theWorld.getWorldBorder.contains(posBlock)*/ ) {
//            blockHitDelay = 5
//            net.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, posBlock, directionFacing))
//            clickBlockCreative(this, posBlock)
//            true
//        }
//
//        else clickBlock(posBlock, directionFacing)
//    }

//    def clickBlockCreative(playerController: PlayerControllerMP, pos: BlockPos /*, facing: EnumFacing*/) {
//
//        playerController.onPlayerDestroyBlock(pos)
//    }
//
//    def onPlayerDestroyBlock(pos: BlockPos): Boolean = {
//
//
//        val world: World = tar.world
//
//        val block: BlockState = world.getBlock(pos)
//
//        if (block.block == Blocks.air) false
//        else {
//
//            val flag: Boolean = block.removedByPlayer(world, pos, tar.player, willHarvest = false)
//            if (flag) block.onBlockDestroyedByPlayer(world, pos)
//            flag
//        }
//
//    }

    def windowClick(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {
        player.openContainer.mouseClicked(x, y, button, player)
        net.sendPacket(new CPacketClickWindow(x, y, button))
    }
}
