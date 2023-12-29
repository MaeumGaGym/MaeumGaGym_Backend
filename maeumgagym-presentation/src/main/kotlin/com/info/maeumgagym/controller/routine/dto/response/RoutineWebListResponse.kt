package com.info.maeumgagym.controller.routine.dto.response

import com.info.maeumgagym.controller.user.dto.UserWebResponse
import com.info.maeumgagym.routine.dto.response.RoutineListResponse

data class RoutineWebListResponse(
    val userInfo: UserWebResponse,
    val routineList: List<RoutineWebResponse>
) {

    companion object {
        fun toWebResponse(res: RoutineListResponse) = RoutineWebListResponse(
            UserWebResponse.toWebResponse(res.userInfo),
            res.routineList.map {
                RoutineWebResponse.toWebResponse(it)
            }.toList()
        )
    }
}
