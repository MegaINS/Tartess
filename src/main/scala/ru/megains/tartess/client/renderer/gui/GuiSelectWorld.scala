package ru.megains.tartess.client.renderer.gui

import ru.megains.tartess.common.entity.player.EntityPlayer
import ru.megains.tartess.client.renderer.gui.base.GuiMenu
import ru.megains.tartess.client.renderer.gui.element.GuiButton
import ru.megains.tartess.common.world.World

class GuiSelectWorld(guiMainMenu: GuiMenu) extends GuiMenu {

    var worldsSlot: Array[GuiSlotWorld] = _
    var buttonSelect: GuiButton = _
    var buttonDelete: GuiButton = _
    var selectWorld: GuiSlotWorld = _

    override def initGui(): Unit = {

        val savesArray = tar.saveLoader.getSavesArray
        worldsSlot = new Array[GuiSlotWorld](savesArray.length)

        for (i <- savesArray.indices) {
            worldsSlot(i) = new GuiSlotWorld(i, savesArray(i), tar)
        }


        buttonSelect = new GuiButton(0, tar, "Select", 100, 100, 200, 50)
        buttonDelete = new GuiButton(1, tar, "Delete", 100, 20, 200, 50)
        buttonList += new GuiButton(2, tar, "CreateWorld", 500, 100, 200, 50)
        buttonList += new GuiButton(5, tar, "Cancel", 500, 20, 200, 50)
        buttonList += buttonSelect
        buttonList += buttonDelete

        buttonDelete.enable = false
        buttonSelect.enable = false


    }


    override def actionPerformed(button: GuiButton): Unit = {
        button.id match {
            case 0 =>
                val saveHandler = tar.saveLoader.getSaveLoader(selectWorld.worldName)
                tar.loadWorld(new World(saveHandler))
                tar.guiManager.setGuiScreen(null)

            case 1 =>
                tar.saveLoader.deleteWorldDirectory(selectWorld.worldName)
                tar.guiManager.setGuiScreen(this)

            case 2 =>
                val saveHandler = tar.saveLoader.getSaveLoader("World " + (worldsSlot.length + 1))
                tar.loadWorld(new World(saveHandler))
                tar.guiManager.setGuiScreen(null)

            case 5 =>
                tar.guiManager.setGuiScreen(guiMainMenu)

            case _ =>
        }
    }

    override def drawScreen(mouseX: Int, mouseY: Int): Unit = {
        super.drawScreen(mouseX, mouseY)
        worldsSlot.foreach(_.draw(mouseX, mouseY))
    }

    override def mouseClicked(x: Int, y: Int, button: Int, player: EntityPlayer): Unit = {
        super.mouseClicked(x, y, button, player)
        if (button == 0) {
            var isSelect = false
            worldsSlot.foreach(
                slot => {
                    if (slot.isMouseOver(x, y)) {
                        slot.select = true
                        selectWorld = slot
                        isSelect = true
                    } else {
                        slot.select = false
                    }
                }
            )
            buttonDelete.enable = isSelect
            buttonSelect.enable = isSelect
            if (!isSelect) selectWorld = null
        }
    }

}
