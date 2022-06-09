package com.example.waternotifier

object Config {
    // source: https://www.ncbi.nlm.nih.gov/pmc/articles/PMC2908954/
    // source: https://www.umsystem.edu/totalrewards/wellness/how-to-calculate-how-much-water-you-should-drink

    const val WOMAN_WATER_DEMAND = 2000
    const val MAN_WATER_DEMAND = 2300

    const val ACTIVITY_BONUS = 700
    const val PASSIVE_MINUS = 300

    // [ml]
    const val CUP_VOLUME = 250
    const val MUG_VOLUME = 350
    const val BOTTLE_VOLUME = 500

    // [%]
    const val WATER_PERCENTAGE = 100
    const val MILK_PERCENTAGE = 90
    const val JUICE_PERCENTAGE = 80
    const val COFFEE_PERCENTAGE = 50
    const val TEA_PERCENTAGE = 60
    const val ALCOHOL_PERCENTAGE = 0

}