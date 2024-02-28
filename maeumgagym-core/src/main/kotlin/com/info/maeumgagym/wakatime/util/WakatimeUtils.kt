package com.info.maeumgagym.wakatime.util

import kotlin.math.pow

object WakatimeUtils {

    private fun findSeconds(level: Double): Double {
        val levelPlusFour = level.plus(4)

        return (
            (level.pow(3) * levelPlusFour) +
                ((level + levelPlusFour) * (level.pow(2) + levelPlusFour))
            ) * 10 + 45000
    }

    fun findLevel(seconds: Long): Int {
        var low = 0.0
        var high = seconds.toDouble()

        while (high - low > 1e-7) {
            val mid = (low + high) / 2.0

            if (findSeconds(mid) < seconds) low = mid else high = mid
        }

        return high.toInt()
    }
}
