package com.info.maeumgagym.routine.controller

import com.info.common.WebAdapter
import com.info.maeumgagym.routine.dto.request.CreateRoutineRequest
import com.info.maeumgagym.routine.port.`in`.CreateRoutineUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/routine")
@WebAdapter
class RoutineController(
    private val createRoutineUseCase: CreateRoutineUseCase
) {
    @PostMapping
    fun createRoutine(@RequestBody createRoutineRequest: CreateRoutineRequest) =
        createRoutineUseCase.createRoutine(createRoutineRequest)
}
