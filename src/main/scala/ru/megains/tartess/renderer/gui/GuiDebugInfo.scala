package ru.megains.tartess.renderer.gui

import java.awt.Color

import ru.megains.tartess.entity.player.GameType.NOT_SET
import ru.megains.tartess.block.data.BlockDirection
import ru.megains.tartess.entity.item.EntityItem
import ru.megains.tartess.entity.player.GameType
import ru.megains.tartess.renderer.gui.base.GuiInGame

class GuiDebugInfo extends GuiInGame with GuiText {

    var lastGameType: GameType = NOT_SET
    var lastSide:BlockDirection = BlockDirection.UP

    var entityAll = 0
    var entity = 0
    var entityItem = 0
    override def initGui(): Unit = {


        addText("position", createString("Player position:", Color.WHITE))
        addText("position.x", createString("x:", Color.WHITE))
        addText("position.y", createString("y:", Color.WHITE))
        addText("position.z", createString("z:", Color.WHITE))
        addText("position.side", createString("side:", Color.WHITE))
        addText("fps", createString("FPS:", Color.WHITE))
        addText("memory", createString("Memory use:", Color.WHITE))
        addText("gameType", createString("Game type: " + lastGameType.name, Color.WHITE))

        addText("entity.all", createString("Entity in world: 0", Color.WHITE))
        addText("entity.item", createString("Entity Item: 0", Color.WHITE))
        addText("entity", createString("Other entity: 0", Color.WHITE))
    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        val height = tar.window.height
        val width = tar.window.width
        drawObject(0, height - 20, 1, text("position"))
        drawObject(0, height - 40, 1, text("position.x"))
        drawObject(0, height - 60, 1, text("position.y"))
        drawObject(0, height - 80, 1, text("position.z"))
        drawObject(0, height - 100, 1, text("position.side"))
        drawObject(0, height - 120, 1, text("gameType"))




        drawObject(width - 250, height - 20, 1, text("memory"))
        drawObject(width - 250, height - 40, 1, text("fps"))
        drawObject(width - 250, height - 60, 1, text("entity.all"))
        drawObject(width - 250, height - 80, 1, text("entity.item"))
        drawObject(width - 250, height - 100, 1, text("entity"))
    }

    override def tick(): Unit = {
        val player = tar.player
        addText("position.x", createString("x: " + player.posX, Color.WHITE))
        addText("position.y", createString("y: " + player.posY, Color.WHITE))
        addText("position.z", createString("z: " + player.posZ, Color.WHITE))


        if(tar.world.entities.size != entityAll){
            entityAll = tar.world.entities.size
            addText("entity.all", createString("Entity in world: "+entityAll, Color.WHITE))
            if(tar.world.entities.count(_.isInstanceOf[EntityItem]) != entityItem){
                entityItem = tar.world.entities.count(_.isInstanceOf[EntityItem])
                addText("entity.item", createString("Entity Item: "+entityItem, Color.WHITE))
            }
            if(entity !=  entityAll - entityItem){
                entity = entityAll - entityItem
                addText("entity", createString("Other entity: " + entity, Color.WHITE))
            }
        }




        val gameType = tar.player.gameType
        if (lastGameType ne gameType) {
            lastGameType = gameType
            addText("gameType", createString("Game type: " + gameType.name, Color.WHITE))
        }
        val side = tar.player.side
        if(side!= lastSide){
            lastSide = side
            addText("position.side", createString("side: "+side.name, Color.WHITE))
        }
        tickI += 1
        if (tickI > 19) {
            tickI = 0
            val usedBytes: Long = (Runtime.getRuntime.totalMemory - Runtime.getRuntime.freeMemory) / 1048576
            addText("memory", createString("Memory use: " + usedBytes + "/" + Runtime.getRuntime.totalMemory / 1048576 + "MB", Color.WHITE))
            addText("fps", createString("FPS: " + tar.lastFrames, Color.WHITE))
        }


    }

    var tickI = 0

}
