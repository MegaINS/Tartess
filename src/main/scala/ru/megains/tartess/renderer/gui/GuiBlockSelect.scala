package ru.megains.tartess.renderer.gui

import java.awt.Color

import ru.megains.old.util.RayTraceResult
import ru.megains.old.utils.Vec3f
import ru.megains.tartess.Tartess
import ru.megains.tartess.block.Block
import ru.megains.tartess.block.data.BlockPos


class GuiBlockSelect extends GuiInGame with GuiText {

    var ray: RayTraceResult = _
    var blockSelect: Block = _

    override def initGui(tar: Tartess): Unit = {


        addText("Block.name", createString("", Color.WHITE))
        addText("Block.side", createString("", Color.WHITE))
        addText("Block.x", createString("", Color.WHITE))
        addText("Block.y", createString("", Color.WHITE))
        addText("Block.z", createString("", Color.WHITE))
       // addText("Block.hp", createString("", Color.WHITE))
       // addText("Block.meta", createString("", Color.WHITE))
    }

    override def tick(): Unit = {
        if (tar.objectMouseOver ne ray) {
            ray = tar.objectMouseOver
            if (ray ne null) {
                val posB: BlockPos = ray.getBlockWorldPos
                val vec: Vec3f = ray.hitVec
             //   val meta = oc.world.getBlockMeta(posB)
               // val hp = oc.world.getBlockHp(posB)
                blockSelect = ray.block
                addText("Block.name", createString(ray.block.name, Color.WHITE))
                addText("Block.x", createString("x: " + posB.x + "  " + vec.x, Color.WHITE))
                addText("Block.y", createString("y: " + posB.y + "  " + vec.y, Color.WHITE))
                addText("Block.z", createString("z: " + posB.z + "  " + vec.z, Color.WHITE))
                addText("Block.side", createString("side: " + ray.sideHit.name, Color.WHITE))
              //  addText("Block.hp", createString("HP: " + hp, Color.WHITE))
               // addText("Block.meta", createString("Meta: "+ meta, Color.WHITE))
            }
        }
    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        if (ray ne null) {
            val weight = 800 / 2 - 100
            val height = 600
            drawObject(weight, height - 20, 1, text("Block.name"))
            drawObject(weight, height - 40, 1, text("Block.x"))
            drawObject(weight, height - 60, 1, text("Block.y"))
            drawObject(weight, height - 80, 1, text("Block.z"))
            drawObject(weight, height - 100, 1, text("Block.side"))
         //   drawObject(weight, height - 120, 1, text("Block.hp"))
          //  drawObject(weight, height - 140, 1, text("Block.meta"))
           // drawItemStack(new ItemStack(blockSelect), 800 / 2 - 150, height - 50)
        }

    }

}
