package ru.megains.old.block

import ru.megains.old.blockdata.BlockWorldPos
import ru.megains.tartess.renderer.texture.{TTextureRegister, TextureAtlas}
import ru.megains.old.world.World
import ru.megains.tartess.block.data.BlockDirection

import scala.util.Random


class BlockGrass(name:String)extends Block(name){

  var aTextureUp:TextureAtlas = null
  var aTextureDown:TextureAtlas = null



  override def randomUpdate(world: World,BlockWorldPos: BlockWorldPos, rand:Random): Unit ={
//   for(i <- 0 to 3){
//     val x = rand.nextInt(3)-1
//     val y = rand.nextInt(3)-1
//     val z = rand.nextInt(3)-1
//
//     if(world.getBlock(BlockWorldPos.sum(new BlockWorldPos(x,y,z)) )== Blocks.dirt) world.setBlock(BlockWorldPos.sum(new BlockWorldPos(x,y,z)),Blocks.grass )
//
//   }

  }

  override def registerTexture(textureRegister: TTextureRegister): Unit ={

    aTexture = textureRegister.registerTexture(name+"_side")
    aTextureUp = textureRegister.registerTexture(name+"_up")
    aTextureDown = textureRegister.registerTexture(name+"_down")
  }
  override def getATexture(blockDirection: BlockDirection): TextureAtlas ={
    blockDirection match {
      case BlockDirection.UP=>aTextureUp
      case BlockDirection.DOWN=>aTextureDown
      case _=>aTexture
    }

  }


}
