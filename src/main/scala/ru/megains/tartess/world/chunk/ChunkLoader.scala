package ru.megains.tartess.world.chunk


import ru.megains.tartess.register.Blocks
import ru.megains.tartess.world.World

import scala.language.postfixOps


object ChunkLoader {



    def load(world: World, chunkX: Int, chunkY: Int, chunkZ: Int): Chunk  = {


        val chunk:Chunk = new Chunk(new ChunkPosition(chunkX,chunkY,chunkZ),world)

        for(i<-  chunk.blockStorage.blocksId.indices){
            if (chunkY < 0) {
               // if(Random.nextBoolean()){
                    chunk.blockStorage.blocksId(i) = Blocks.getIdByBlock(  Blocks.dirt) toByte
               // }

            }
        }

        chunk
    }



    var sizeWorld:Int =0

   // private val LOCATION:String = getClass.getClassLoader.getResource("").getPath
   // private val WORLD_LOCATION: String =LOCATION+ "world/chunk/"
    //File(WORLD_LOCATION).createDirectory()


//    def load(world:World,x:Int,y:Int,z:Int):Chunk ={
//
//        val chunk: Array[Byte] = new Array[Byte](4096)
//
//       // if( !load(Chunk.getIndex(x,y,z) toString,chunk)){
//
//            for(i<- chunk.indices){
//                if (y < 0) {
//                   // chunk(i) = Block.getIdByBlock(Blocks.grass) toByte
//                }
//                else {
//                   // chunk(i) =  Block.getIdByBlock(Blocks.air) toByte
//                }
//            }
//      //  }
//        new Chunk(world,new ChunkPosition(x,y,z),chunk)
//    }



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
