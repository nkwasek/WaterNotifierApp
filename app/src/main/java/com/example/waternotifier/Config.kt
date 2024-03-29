package com.example.waternotifier

object Config {

    // source: https://www.umsystem.edu/totalrewards/wellness/how-to-calculate-how-much-water-you-should-drink
    const val FEMALE_WATER_RATE = 0.9
    const val MALE_WATER_RATE = 1.1
    const val ACTIVITY_BONUS = 700
    const val PASSIVE_MINUS = 300

    // [ml]
    const val CUP_VOLUME = 250
    const val MUG_VOLUME = 350
    const val BOTTLE_VOLUME = 500

    // [%]  // source: https://www.ncbi.nlm.nih.gov/pmc/articles/PMC2908954/
    const val WATER_PERCENTAGE = 100
    const val MILK_PERCENTAGE = 90
    const val JUICE_PERCENTAGE = 80
    const val COFFEE_PERCENTAGE = 45
    const val TEA_PERCENTAGE = 50
    const val ALCOHOL_PERCENTAGE = 30

    const val MAX_VOLUME = 5000
    const val PROGRESS_SEEKBAR = 100

    val WEEK_DAYS = listOf<String>("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
}