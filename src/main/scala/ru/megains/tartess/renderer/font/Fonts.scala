package ru.megains.tartess.renderer.font

import ru.megains.tartess.Tartess

object Fonts {
    val fontRender: FontRender = Tartess.tartess.fontRender
    val timesNewRomanR: Font = fontRender.loadFont("TimesNewRomanR")
}
