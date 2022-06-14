package com.example.waternotifier

import java.util.*
import kotlin.collections.ArrayList

object LocalVariables {
    var Goal = 2000
    var Progress = 0
    var DayStart = "6:00"
    var DayEnd = "00:00"
    var YesterdayEnd = "00:00"

    var NotificationPeriod = 60
    var NotificationPeriodUserValue = -1

    var Today = Calendar.DAY_OF_WEEK
    var Yesterday = Today - 1

    val xAxis = mutableListOf<String>()
    val goals = mutableListOf<Float>()
    val progress = mutableListOf<Float>()

    fun calculateNotificationPeriod() {
        var endH = Integer.parseInt(DayEnd.split(":")[0])
        var endM = Integer.parseInt(DayEnd.split(":")[1])
        var startH = Integer.parseInt(DayStart.split(":")[0])
        var startM = Integer.parseInt(DayStart.split(":")[1])

        var end = endH * 60 + endM
        var start = startH * 60 + startM

        var notificationsCount = Goal / Config.CUP_VOLUME

        if(end < start) {
            NotificationPeriod = (1440 - start + end) / notificationsCount
        }
        else {
            NotificationPeriod = (end - start) / notificationsCount
        }
    }

}