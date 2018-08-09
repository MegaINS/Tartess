package ru.megains.tartess.server

import ru.megains.tartess.common.block.data.{BlockDirection, BlockPos, BlockState}
import ru.megains.tartess.common.entity.player.GameType
import ru.megains.tartess.common.item.ItemBlock
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.common.network.packet.play.server.SPacketBlockChange
import ru.megains.tartess.common.utils.RayTraceResult
import ru.megains.tartess.common.world.World
import ru.megains.tartess.server.entity.EntityPlayerMP

class PlayerInteractionManager(world: World) {



    var thisPlayerMP: EntityPlayerMP = _
    var blockReachDistance: Double = 5.0d *16
    var gameType: GameType = GameType.CREATIVE
    var isDestroyingBlock: Boolean = false

    def setGameType(gameTypeIn: GameType): Unit = {
        gameType = gameTypeIn

    }






    def isCreative: Boolean = gameType.isCreative

    def onBlockClicked(pos: BlockPos, side: BlockDirection) {

        if (isCreative)  tryHarvestBlock(pos)

    }

    def tryHarvestBlock(pos: BlockPos): Boolean = {

        var flag1: Boolean = false
        if (isCreative) {
            flag1 = removeBlock(pos)
             thisPlayerMP.connection.sendPacket(new SPacketBlockChange(world, pos))
        }

        flag1

    }

    private def removeBlock(pos: BlockPos): Boolean = removeBlock(pos, canHarvest = false)

    private def removeBlock(pos: BlockPos, canHarvest: Boolean): Boolean = {
         val block: BlockState = world.getBlock(pos)
         val flag: Boolean = block.removedByPlayer(world, pos, thisPlayerMP, canHarvest)
         if (flag) block.onBlockDestroyedByPlayer(world, pos)
         flag

    }


    def getBlockReachDistance: Double = blockReachDistance




    def processRightClickBlock(rayTrace: RayTraceResult,blockState:BlockState): Unit = {


        val itemstack: ItemPack = thisPlayerMP.getHeldItem
        val block: BlockState = world.getBlock(rayTrace.blockPos)

        if (block.onBlockActivated(world, rayTrace.blockPos, thisPlayerMP, itemstack, rayTrace.sideHit, rayTrace.hitVec.x, rayTrace.hitVec.y, rayTrace.hitVec.z)){

        }else{
            if(itemstack!= null) {
                itemstack.item match {
                    case _: ItemBlock =>
                        if (blockState != null) {
                            itemstack.onItemUse(thisPlayerMP, world, blockState, rayTrace.sideHit, rayTrace.hitVec.x, rayTrace.hitVec.y, rayTrace.hitVec.z)
                            thisPlayerMP.connection.sendPacket(new SPacketBlockChange(world, blockState.pos))
                        }

                    case _ =>
                }
            }

        }


    }
}

