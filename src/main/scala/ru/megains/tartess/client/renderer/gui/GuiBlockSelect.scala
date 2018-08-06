package ru.megains.tartess.client.renderer.gui

import java.awt.Color

import ru.megains.tartess.common.block.Block
import ru.megains.tartess.common.block.data.BlockPos
import ru.megains.tartess.common.item.itemstack.ItemPack
import ru.megains.tartess.client.renderer.gui.base.GuiInGame
import ru.megains.tartess.common.utils.{RayTraceResult, Vec3f}


class GuiBlockSelect extends GuiInGame with GuiText {

    var ray: RayTraceResult = _
    var blockSelect: Block = _

    override def initGui(): Unit = {


        addText("Block.name", createString("", Color.WHITE))
        addText("Block.side", createString("", Color.WHITE))
        addText("Block.x", createString("", Color.WHITE))
        addText("Block.y", createString("", Color.WHITE))
        addText("Block.z", createString("", Color.WHITE))

    }

    override def tick(): Unit = {
        if (tar.objectMouseOver != ray) {
            ray = tar.objectMouseOver
            if (ray  != null) {
                val posB: BlockPos = ray.blockPos
                val vec: Vec3f = ray.hitVec

                blockSelect = ray.block
                addText("Block.name", createString(ray.block.name, Color.WHITE))
                addText("Block.x", createString("x: " + posB.x + "  " + (vec.x - posB.x), Color.WHITE))
                addText("Block.y", createString("y: " + posB.y + "  " + (vec.y - posB.y), Color.WHITE))
                addText("Block.z", createString("z: " + posB.z + "  " + (vec.z - posB.z), Color.WHITE))
                addText("Block.side", createString("side: " + ray.sideHit.name, Color.WHITE))

            }
        }
    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        if (ray ne null) {
            val weight = tar.window.width / 2 - 100
            val height = tar.window.height
            drawObject(weight, height - 20, 1, text("Block.name"))
            drawObject(weight, height - 40, 1, text("Block.x"))
            drawObject(weight, height - 60, 1, text("Block.y"))
            drawObject(weight, height - 80, 1, text("Block.z"))
            drawObject(weight, height - 100, 1, text("Block.side"))
            drawItemStack(new ItemPack(blockSelect), tar.window.width / 2 - 150, height - 50)
        }

    }

}
