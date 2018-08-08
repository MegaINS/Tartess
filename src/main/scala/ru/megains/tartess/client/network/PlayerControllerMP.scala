package ru.megains.tartess.client.network

import ru.megains.tartess.client.Tartess
import ru.megains.tartess.client.entity.EntityPlayerSP
import ru.megains.tartess.client.network.handler.NetHandlerPlayClient
import ru.megains.tartess.common.block.data.BlockState
import ru.megains.tartess.common.entity.player.{EntityPlayer, GameType}
import ru.megains.tartess.common.item.ItemBlock
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.common.network.packet.play.client._
import ru.megains.tartess.common.utils.RayTraceType
import ru.megains.tartess.common.world.World

class PlayerControllerMP(tar:Tartess,val net: NetHandlerPlayClient) {



    var isHittingBlock: Boolean = false
    var blockHitDelay: Int = 0
    var currentPlayerItem: Int = 0

    var currentGameType: GameType = GameType.CREATIVE

    def setGameType(gameType: GameType): Unit = {
        currentGameType = gameType
    }

    def sendQuittingDisconnectingPacket(): Unit = {
        net.netManager.closeChannel("Quitting")
    }
    def createClientPlayer(world: World): EntityPlayerSP = {
        new EntityPlayerSP(tar, world, net)
    }

    def rightClickMouse(): Unit = {
        syncCurrentPlayItem()
        net.sendPacket(new CPacketPlayerMouse(1,0))
        val rayTrace = tar.objectMouseOver

        rayTrace.rayTraceType match {
            case RayTraceType.BLOCK  =>
                val itemstack: ItemPack = tar.player.getHeldItem
                val block: BlockState = tar.world.getBlock(rayTrace.blockPos)

                if (block.onBlockActivated(tar.world, rayTrace.blockPos, tar.player, itemstack, rayTrace.sideHit, rayTrace.hitVec.x, rayTrace.hitVec.y, rayTrace.hitVec.z)){

                }else{
                    if(itemstack!= null){
                        itemstack.item match {
                            case _:ItemBlock =>
//                                val blockState = tar.blockSelectPosition
//                                if(blockState!=null){
//                                    itemstack.onItemUse(tar.player, tar.world,blockState, rayTrace.sideHit, rayTrace.hitVec.x, rayTrace.hitVec.y, rayTrace.hitVec.z)
//                                }

                            case _ =>
                        }
                    }



                }
            case RayTraceType.VOID  =>
            case RayTraceType.ENTITY  =>
        }
    }

    def leftClickMouse(): Unit = {
        syncCurrentPlayItem()
        net.sendPacket(new CPacketPlayerMouse(0,0))
        val rayTrace = tar.objectMouseOver

        rayTrace.rayTraceType match {
            case RayTraceType.BLOCK  =>
            case RayTraceType.VOID  =>
            case RayTraceType.ENTITY  =>
        }
    }



    def syncCurrentPlayItem() {
        val i: Int = tar.player.inventory.stackSelect
        if (i != currentPlayerItem) {
            currentPlayerItem = i
            net.sendPacket(new CPacketHeldItemChange(currentPlayerItem))
        }
    }
    def windowClick(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {
        player.openContainer.mouseClicked(x, y, button, player)
        net.sendPacket(new CPacketClickWindow(x, y, button))
    }


}
