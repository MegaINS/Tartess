package ru.megains.tartess.client.network

import ru.megains.tartess.client.Tartess
import ru.megains.tartess.client.entity.EntityPlayerSP
import ru.megains.tartess.client.network.handler.NetHandlerPlayClient
import ru.megains.tartess.common.block.Block
import ru.megains.tartess.common.block.data.{BlockDirection, BlockPos}
import ru.megains.tartess.common.entity.player.{EntityPlayer, GameType}
import ru.megains.tartess.common.item.Item
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.common.utils.EnumActionResult.EnumActionResult
import ru.megains.tartess.common.utils.{ActionResult, EnumActionResult, Vec3f}
import ru.megains.tartess.common.world.World

class PlayerControllerMP(tar:Tartess,net: NetHandlerPlayClient) {

    var isHittingBlock: Boolean = false
    var blockHitDelay: Int = 0
    var currentPlayerItem: Int = 0
    def setGameType(gameType: GameType): Unit = {
        currentGameType = gameType
        //currentGameType.configurePlayerCapabilities(this.mc.thePlayer.capabilities)
    }

    def createClientPlayer(world: World): EntityPlayerSP = {
        new EntityPlayerSP(tar, world, net)
    }


    var currentGameType: GameType = GameType.CREATIVE


    def processRightClickBlock(player: EntityPlayer, worldIn: World, stack: ItemPack, pos: BlockPos, facing: BlockDirection, vec: Vec3f): EnumActionResult = {

        syncCurrentPlayItem()
        val f: Float = (vec.x - pos.x.toDouble).toFloat
        val f1: Float = (vec.y - pos.y.toDouble).toFloat
        val f2: Float = (vec.z - pos.z.toDouble).toFloat
        var flag: Boolean = false

       // var result: EnumActionResult = EnumActionResult.PASS

        val item: Item = if (stack == null) null
        else stack.item
        val ret: EnumActionResult = if (item == null) EnumActionResult.PASS
        else item.onItemUseFirst(stack, player, worldIn, pos, facing, f, f1, f2)
        if (ret ne EnumActionResult.PASS) return ret
        val block: Block = worldIn.getBlock(pos).block
        flag = block.onBlockActivated(worldIn, pos, player, stack, facing, f, f1,f2)
       // if (flag) result = EnumActionResult.SUCCESS


        if (!flag) {
            if (stack == null){
                EnumActionResult.PASS
            } else {
                val j: Int = stack.stackSize
                val enumactionresult: EnumActionResult = stack.onItemUse(player, worldIn, tar.blockSelectPosition, facing, f, f1, f2)

                stack.stackSize = j
                enumactionresult
            }
        }
        else EnumActionResult.SUCCESS

    }

    def processRightClick(player: EntityPlayer, worldIn: World, stack: ItemPack): EnumActionResult = {
        syncCurrentPlayItem()

        val i: Int = stack.stackSize
        val actionresult: ActionResult[ItemPack] = stack.useItemRightClick(worldIn, player)
        val itemstack: ItemPack = actionresult.result
        if ((itemstack != stack) || itemstack.stackSize != i) {
            player.setHeldItem(itemstack)
            if (itemstack.stackSize <= 0) {
                player.setHeldItem(null)
            }
        }
        actionresult.`type`

    }

    private def syncCurrentPlayItem() {
        val i: Int = tar.player.inventory.stackSelect
        if (i != currentPlayerItem) {
            currentPlayerItem = i
        }
    }
}
