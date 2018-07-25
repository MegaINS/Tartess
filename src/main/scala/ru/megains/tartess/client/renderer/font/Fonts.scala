package ru.megains.tartess.client.renderer.font

import ru.megains.tartess.client.Tartess

object Fonts {
    val fontRender: FontRender = Tartess.tartess.fontRender
    val timesNewRomanR: Font = fontRender.loadFont("TimesNewRomanR")
}
