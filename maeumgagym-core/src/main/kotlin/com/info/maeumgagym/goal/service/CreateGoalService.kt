package com.info.maeumgagym.goal.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.goal.dto.request.CreateGoalRequest
import com.info.maeumgagym.goal.exception.StartDateIsAfterEndDateException
import com.info.maeumgagym.goal.model.Goal
import com.info.maeumgagym.goal.port.`in`.CreateGoalUseCase
import com.info.maeumgagym.goal.port.out.SaveGoalPort

@UseCase
class CreateGoalService(
    private val saveGoalPort: SaveGoalPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : CreateGoalUseCase {

    override fun createGoal(req: CreateGoalRequest) {
        val user = readCurrentUserPort.readCurrentUser()

        if (req.startDate.isAfter(req.endDate)) {
            throw StartDateIsAfterEndDateException
        }

        req.run {
            saveGoalPort.save(
                Goal(
                    user = user,
                    title = title,
                    content = content,
                    startDate = startDate,
                    endDate = endDate
                )
            )
        }
    }
}
