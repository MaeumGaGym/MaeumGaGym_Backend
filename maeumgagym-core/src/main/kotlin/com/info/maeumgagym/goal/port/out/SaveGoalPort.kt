package com.info.maeumgagym.goal.port.out

import com.info.maeumgagym.goal.model.Goal

interface SaveGoalPort {
    fun save(goal: Goal): Goal
}
