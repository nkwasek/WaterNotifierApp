package com.example.waternotifier

import java.util.*
import kotlin.collections.ArrayList

object LocalVariables {
    var Goal = 2000
    var Progress = 0
    var DayStart = "6:00"
    var DayEnd = "00:00"
    var YesterdayEnd = "00:00"

    var Today = Calendar.DAY_OF_WEEK
    var Yesterday = Today - 1

    val xAxis = mutableListOf<String>()
    val goals = mutableListOf<Float>()
    val progress = mutableListOf<Float>()

}