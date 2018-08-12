package ru.megains.tartess.client.world

import ru.megains.tartess.client.network.handler.NetHandlerPlayClient
import ru.megains.tartess.client.renderer.world.WorldRenderer
import ru.megains.tartess.common.block.data.BlockState
import ru.megains.tartess.common.world.chunk.data.ChunkPosition
import ru.megains.tartess.common.world.{IChunkProvider, World}

class WorldClient(net: NetHandlerPlayClient) extends World(new SaveHandlerMP){

    var worldRenderer: WorldRenderer = _

    def doPreChunk(pos: ChunkPosition, loadChunk: Boolean) = {
        if (loadChunk) chunkProvider.loadChunk(pos)
        else {/*
              this.chunkProvider.unloadChunk(chunkX, chunkZ)
                this.markBlockRangeForRenderUpdate(chunkX * 16, 0, chunkZ * 16, chunkX * 16 + 15, 256, chunkZ * 16 + 15)
            */
        }
    }

    def invalidateRegionAndSetBlock( block: BlockState): Boolean = {
        /*
          val i: Int = pos.getX
          val j: Int = pos.getY
           val k: Int = pos.getZ
         invalidateBlockReceiveRegion(i, j, k, i, j, k)
 */
        setBlock(block)
       // setBlock(pos, block, 3)

    }


    override val chunkProvider: IChunkProvider = new ChunkProviderClient(this)

    override def save(): Unit = {}
/*
    override def setBlock( block: BlockState, flag: Int): Boolean = {
        if (super.setBlock(block, flag)) {
            worldRenderer.reRender(pos)
            true
        } else {
            false
        }
    }
*/
}
