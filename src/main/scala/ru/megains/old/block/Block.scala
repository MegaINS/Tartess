package ru.megains.old.block

import ru.megains.old.blockdata.{BlockSize, BlockWorldPos}
import ru.megains.old.graph.renderer.block.RenderBlockGlass
import ru.megains.old.physics.{AxisAlignedBB, BlockAxisAlignedBB}
import ru.megains.old.register.{Blocks, GameRegister}
import ru.megains.tartess.renderer.texture.{TTextureRegister, TextureAtlas}
import ru.megains.old.util.RayTraceResult
import ru.megains.old.world.World
import ru.megains.tartess.block.data.BlockDirection
import ru.megains.tartess.utils.Vec3f

import scala.util.Random


class Block(val name:String) {




  var aTexture: TextureAtlas = null

  var  textureName: String =""

  def randomUpdate(world: World,blockPos: BlockWorldPos, rand:Random): Unit ={

  }


  def getATexture(blockDirection: BlockDirection): TextureAtlas =aTexture

  def setTextureName(textureName:String): Block ={
    this.textureName = textureName
    this
  }

  def registerTexture(textureRegister: TTextureRegister): Unit ={
    aTexture = textureRegister.registerTexture(name)
  }

  def isFullBlock:Boolean = true


  def isOpaqueCube: Boolean = true

    def getPhysicsBody:BlockAxisAlignedBB = BlockSize.FULL_AABB

  def getSelectedBoundingBox ( pos:BlockWorldPos/*,offset:MultiBlockPos*/):AxisAlignedBB= BlockSize.FULL_AABB//.sum(offset.floatX,offset.floatY,offset.floatZ)

  def getBoundingBox ( pos:BlockWorldPos/*,offset:MultiBlockPos*/):AxisAlignedBB = getSelectedBoundingBox(pos/*,offset*/).sum(pos.worldX,pos.worldY,pos.worldZ)

  def collisionRayTrace( world:World, pos:BlockWorldPos,  start: Vec3f,  end: Vec3f/*,offset:MultiBlockPos*/): RayTraceResult ={


    val vec3d =  new Vec3f(start).sub(pos.worldX, pos.worldY, pos.worldZ)
    val vec3d1 =  new Vec3f(end).sub(pos.worldX, pos.worldY, pos.worldZ)
    val rayTraceResult = getSelectedBoundingBox(pos/*,offset*/).calculateIntercept(vec3d, vec3d1)
    if(rayTraceResult == null) {
        null
    } else{
        null
       // new RayTraceResult(rayTraceResult.hitVec.add(pos.worldX, pos.worldY, pos.worldZ), rayTraceResult.sideHit,null/* new BlockWorldPos(pos,offset.x,offset.y,offset.z)*/ ,this)
    }
  }

  def isAir: Boolean = false

  def getLayerRender:Int = 0

}

object Block{
  def getIdByBlock(block: Block) = {
    GameRegister.getIdByBlock(block)
  }


  def getBlockById(id:Int):Block ={
    val block:Block = GameRegister.getBlockById(id)
    if(block==null){
      Blocks.air
    }else{
      block
    }
  }

  def initBlocks(): Unit ={

      GameRegister.registerBlock(1,new BlockAir("air"))
      GameRegister.registerBlock(2,new Block("stone"))
      GameRegister.registerBlock(3,new Block("dirt"))
      GameRegister.registerBlock(4,new BlockGrass("grass"))
      GameRegister.registerBlock(5,new BlockGlass("glass"))
      GameRegister.registerBlock(6,new BlockMicroTest("micro0",0))
      GameRegister.registerBlock(7,new BlockMicroTest("micro1",1))
      GameRegister.registerBlock(8,new BlockMicroTest("micro2",2))
      GameRegister.registerBlockRender(Blocks.glass,RenderBlockGlass)
  }






}

