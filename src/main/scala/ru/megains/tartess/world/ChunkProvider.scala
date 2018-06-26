package ru.megains.tartess.world

import ru.megains.tartess.block.data.{BlockPos, BlockState}
import ru.megains.tartess.register.Blocks
import ru.megains.tartess.world.chunk.{Chunk, ChunkLoader, ChunkVoid}

import scala.collection.mutable
import scala.util.Random

class ChunkProvider(world: World) {

    ChunkProvider.voidChunk = new ChunkVoid(world)
    val chunkMap = new mutable.HashMap[Long,Chunk]()

    def provideChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {

        var chunk: Chunk = loadChunk(chunkX, chunkY, chunkZ)

        if (chunk == null) {
            val i: Long = Chunk.getIndex(chunkX, chunkY, chunkZ)
            try {
                chunk = ChunkLoader.load(world,chunkX,chunkY,chunkZ)
            } catch {
                case throwable: Throwable =>
                    throwable.printStackTrace()
            }
            chunkMap += i -> chunk
        }





        chunk
    }




    def loadChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        var chunk: Chunk = getLoadedChunk(chunkX, chunkY, chunkZ)
        if (chunk == null) {
            chunk = ChunkLoader.load(world,chunkX,chunkY,chunkZ)




            chunk.setBlock(  new BlockState(Blocks.stone,new BlockPos(chunk.position.minXP +  0  ,chunk.position.minYP + 0  ,chunk.position.minZP + 2 )))
            chunk.setBlock(  new BlockState(Blocks.test0,new BlockPos(chunk.position.minXP +  16  ,chunk.position.minYP +  1  ,chunk.position.minZP + 2 )))
            chunk.setBlock(  new BlockState(Blocks.test1,new BlockPos(chunk.position.minXP +  32  ,chunk.position.minYP +  2  ,chunk.position.minZP + 2 )))
            chunk.setBlock(  new BlockState(Blocks.test2,new BlockPos(chunk.position.minXP +  48  ,chunk.position.minYP +  3  ,chunk.position.minZP + 2 )))

            chunk.setBlock(  new BlockState(Blocks.test0,new BlockPos(chunk.position.minXP +   Random.nextInt(60)  ,chunk.position.minYP +   Random.nextInt(60)  ,chunk.position.minZP +  Random.nextInt(60) )))
            chunk.setBlock(  new BlockState(Blocks.test1,new BlockPos(chunk.position.minXP +   Random.nextInt(60)  ,chunk.position.minYP +   Random.nextInt(60)  ,chunk.position.minZP +  Random.nextInt(60) )))
            chunk.setBlock(  new BlockState(Blocks.test2,new BlockPos(chunk.position.minXP +   Random.nextInt(60)  ,chunk.position.minYP +   Random.nextInt(60)  ,chunk.position.minZP +  Random.nextInt(60) )))






            if (chunk != null) {
                chunkMap += Chunk.getIndex(chunkX, chunkY, chunkZ) -> chunk
            }
        }
        chunk
    }

    def getLoadedChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {
        chunkMap.getOrElse(Chunk.getIndex(chunkX, chunkY, chunkZ), null)
    }
}
object ChunkProvider{
    var voidChunk:Chunk = _
}
