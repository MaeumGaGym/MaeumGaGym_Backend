package com.info.maeumgagym.controller.routine.dto

import com.info.maeumgagym.controller.user.dto.UserWebResponse
import com.info.maeumgagym.routine.dto.response.RoutineListResponse

data class RoutineWebListResponse(
    val userInfo: UserWebResponse,
    val routineList: List<RoutineWebResponse>
) {

    companion object {
        fun toWebResponse(res: RoutineListResponse) = RoutineWebListResponse(
            UserWebResponse.toWrbResponse(res.userInfo),
            res.routineList.map {
                RoutineWebResponse.toWebResponse(it)
            }.toList()
        )
    }
}
