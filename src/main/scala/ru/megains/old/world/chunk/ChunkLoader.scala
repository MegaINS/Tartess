package ru.megains.old.world.chunk

import ru.megains.old.block.Block
import ru.megains.old.position.ChunkPosition
import ru.megains.old.register.Blocks
import ru.megains.old.world.World
import ru.megains.tartess

import scala.language.postfixOps


object ChunkLoader {



    def load(world: tartess.world.World, chunkX: Int, chunkY: Int, chunkZ: Int): tartess.world.chunk.Chunk  = {


        val chunk:tartess.world.chunk.Chunk = new tartess.world.chunk.Chunk(new tartess.world.chunk.ChunkPosition(chunkX,chunkY,chunkZ),world)

        for(i<-  chunk.blockStorage.blocksId.indices){
            if (chunkY < 0) {
                chunk.blockStorage.blocksId(i) =  tartess.register.Blocks.getIdByBlock(  tartess.register.Blocks.dirt) toByte
            }
            else {
                chunk.blockStorage.blocksId(i) =   tartess.register.Blocks.getIdByBlock( tartess.register.Blocks.air) toByte
            }
        }

        chunk
    }



    var sizeWorld:Int =0

   // private val LOCATION:String = getClass.getClassLoader.getResource("").getPath
   // private val WORLD_LOCATION: String =LOCATION+ "world/chunk/"
    //File(WORLD_LOCATION).createDirectory()


    def load(world:World,x:Int,y:Int,z:Int):Chunk ={

        val chunk: Array[Byte] = new Array[Byte](4096)

       // if( !load(Chunk.getIndex(x,y,z) toString,chunk)){

            for(i<- chunk.indices){
                if (y < 0) {
                    chunk(i) = Block.getIdByBlock(Blocks.grass) toByte
                }
                else {
                    chunk(i) =  Block.getIdByBlock(Blocks.air) toByte
                }
            }
      //  }
        new Chunk(world,new ChunkPosition(x,y,z),chunk)
    }



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
