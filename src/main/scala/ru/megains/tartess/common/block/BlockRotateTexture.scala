package ru.megains.tartess.common.block
import ru.megains.tartess.client.renderer.texture.TextureAtlas
import ru.megains.tartess.common.physics.{AABB, AABBs, BoundingBox, BoundingBoxes}

import scala.collection.mutable

class BlockRotateTexture extends Block("blockRotateTexture") {


    override val blockBodies: AABBs  = new AABBs(mutable.HashSet(new AABB(16)))
    override val boundingBoxes: BoundingBoxes = new BoundingBoxes(mutable.HashSet(  new BoundingBox(16)))

    var aTexture0: TextureAtlas = _
    var aTexture1: TextureAtlas = _
    var aTexture2: TextureAtlas = _
    var aTexture3: TextureAtlas = _
    var aTexture4: TextureAtlas = _
    var aTexture5: TextureAtlas = _


//    override def getATexture(blockState: BlockState, blockDirection: BlockDirection, world: World): TextureAtlas ={
//
//        blockDirection match {
//            case BlockDirection.UP => aTexture0
//            case BlockDirection.DOWN => aTexture5
//            case BlockDirection.WEST =>
//                blockState.blockDirection match {
//                    case BlockDirection.WEST => aTexture2
//                    case BlockDirection.NORTH => aTexture3
//                    case BlockDirection.SOUTH => aTexture4
//                    case _=> aTexture1
//                }
//            case BlockDirection.NORTH =>
//                blockState.blockDirection match {
//                    case BlockDirection.WEST => aTexture4
//                    case BlockDirection.NORTH => aTexture2
//                    case BlockDirection.SOUTH => aTexture1
//                    case _ => aTexture3
//                }
//            case BlockDirection.SOUTH =>
//                blockState.blockDirection match {
//                    case BlockDirection.WEST => aTexture3
//                    case BlockDirection.NORTH => aTexture1
//                    case BlockDirection.SOUTH => aTexture2
//                    case _ => aTexture4
//                }
//            case _ =>
//                blockState.blockDirection match {
//                    case BlockDirection.WEST => aTexture1
//                    case BlockDirection.NORTH => aTexture4
//                    case BlockDirection.SOUTH => aTexture3
//                    case _ => aTexture2
//                }
//        }
//    }
//
//
//    override def getATexture(pos: BlockPos, blockDirection: BlockDirection, world: World): TextureAtlas ={
//        blockDirection match {
//            case BlockDirection.UP => aTexture0
//            case BlockDirection.DOWN => aTexture5
//            case BlockDirection.WEST => aTexture1
//            case BlockDirection.EAST => aTexture2
//            case BlockDirection.NORTH => aTexture3
//            case BlockDirection.SOUTH => aTexture4
//            case _ => null
//        }
//    }
//
//    override def registerTexture(textureRegister: TTextureRegister): Unit = {
//        aTexture0 = textureRegister.registerTexture("0")
//        aTexture1 = textureRegister.registerTexture("1")
//        aTexture2 = textureRegister.registerTexture("2")
//        aTexture3 = textureRegister.registerTexture("3")
//        aTexture4 = textureRegister.registerTexture("4")
//        aTexture5 = textureRegister.registerTexture("5")
//    }




}
