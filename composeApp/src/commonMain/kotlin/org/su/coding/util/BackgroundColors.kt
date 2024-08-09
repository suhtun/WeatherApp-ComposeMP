package org.su.coding.util

import androidx.compose.ui.graphics.Color
import ui.theme.Candy
import ui.theme.Candy500
import ui.theme.Concrete
import ui.theme.Concrete500
import ui.theme.PeterRiver
import ui.theme.PeterRiver500
import ui.theme.Purpel
import ui.theme.Purpel500
import ui.theme.Sunny
import ui.theme.Sunny500
import ui.theme.Turquoise
import ui.theme.Turquoise500
import ui.theme.Emerald
import ui.theme.Emerald500

fun getBackgroundColor(code:Int): Pair<Color,Color>{
    return when(code) {
        0 -> Pair(Sunny, Sunny500)
        1 -> Pair(Candy, Candy500)
        2-> Pair(Purpel, Purpel500)
        3-> Pair(Emerald, Emerald500)
        4-> Pair(Turquoise, Turquoise500)
        5-> Pair(PeterRiver, PeterRiver500)
        6-> Pair(Concrete, Concrete500)
        else -> Pair(Sunny, Sunny500)
    }
}