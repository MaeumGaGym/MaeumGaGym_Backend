package com.info.maeumgagym.goal.port.`in`

import com.info.maeumgagym.goal.dto.request.CreateGoalRequest

interface CreateGoalUseCase {
    fun createGoal(req: CreateGoalRequest)
}
