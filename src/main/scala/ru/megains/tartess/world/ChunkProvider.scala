package ru.megains.tartess.world


import ru.megains.old.world.chunk.ChunkLoader
import ru.megains.tartess.block.data.{BlockPos, BlockState}
import ru.megains.tartess.register.Blocks
import ru.megains.tartess.world.chunk.Chunk

import scala.collection.mutable

class ChunkProvider(world: World) {


    val chunkMap = new mutable.HashMap[Long,Chunk]()

    def provideChunk(chunkX: Int, chunkY: Int, chunkZ: Int): Chunk = {

        var chunk: Chunk = loadChunk(chunkX, chunkY, chunkZ)

        if (chunk == null) {
            val i: Long = Chunk.getIndex(chunkX, chunkY, chunkZ)
            try {
                chunk = ChunkLoader.load(world,chunkX,chunkY,chunkZ)
                chunk.setBlock(  new BlockState(Blocks.stone,new BlockPos(0,0,2)))
                chunk.setBlock(  new BlockState(Blocks.stone,new BlockPos(8,1,2)))
                chunk.setBlock(  new BlockState(Blocks.stone,new BlockPos(16,2,2)))
                chunk.setBlock(  new BlockState(Blocks.stone,new BlockPos(24,3,2)))
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
            chunk.setBlock(  new BlockState(Blocks.stone,new BlockPos(0,0,2)))
            chunk.setBlock(  new BlockState(Blocks.stone,new BlockPos(8,1,2)))
            chunk.setBlock(  new BlockState(Blocks.stone,new BlockPos(16,2,2)))
            chunk.setBlock(  new BlockState(Blocks.stone,new BlockPos(24,3,2)))
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
