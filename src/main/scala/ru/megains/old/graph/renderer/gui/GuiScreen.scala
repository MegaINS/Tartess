package ru.megains.old.graph.renderer.gui

import ru.megains.old.graph.Renderer


abstract class GuiScreen extends Gui{

    def init():Unit

    def render(renderer: Renderer):Unit

    def cleanup():Unit

}
