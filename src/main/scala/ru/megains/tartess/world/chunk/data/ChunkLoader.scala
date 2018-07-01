package ru.megains.tartess.world.chunk.data

import ru.megains.tartess.register.Blocks
import ru.megains.tartess.world.World
import ru.megains.tartess.world.chunk.Chunk

import scala.language.postfixOps


object ChunkLoader {

    def load(world: World, chunkX: Int, chunkY: Int, chunkZ: Int): Chunk  = {


        val chunk:Chunk = new Chunk(new ChunkPosition(chunkX,chunkY,chunkZ),world)

        for(i<-  chunk.blockStorage.blocksId.indices){
            if (chunkY < 0) {
                chunk.blockStorage.blocksId(i) = Blocks.getIdByBlock(  Blocks.dirt) toByte

            }
        }

        chunk
    }



    var sizeWorld:Int =0



    def load(fileName:String,data: Array[Byte]): Boolean = {

        try {
          //  val e: DataInputStream = new DataInputStream(new GZIPInputStream(new FileInputStream(new JFile(WORLD_LOCATION + fileName+".dat"))))
          //  e.readFully(data)
           // e.close()
            true
        }
        catch {
            case var3: Exception =>
                println("Error load chunk " + fileName+".dat")
                false
        }


    }

    def save(fileName:String,data: Array[Byte]) {


        try {
          //  val e: DataOutputStream = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(new JFile(WORLD_LOCATION + fileName+".dat"))))
           // e.write(data)
            //e.close()
        }
        catch {
            case var2: Exception =>
                println("Error save chunk " + fileName+".dat")
        }
    }

}
