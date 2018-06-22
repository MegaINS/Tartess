package ru.megains.old.graph.renderer.api

import ru.megains.old.block.Block
import ru.megains.old.blockdata.BlockWorldPos
import ru.megains.old.world.World


abstract class ARenderBlock {

  def render(block: Block,world: World,posWorld:BlockWorldPos,posRender:BlockWorldPos/*,offset:MultiBlockPos = MultiBlockPos.default*/)
}
