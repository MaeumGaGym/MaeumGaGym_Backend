package com.info.maeumgagym.controller.routine

import com.info.common.WebAdapter
import com.info.maeumgagym.routine.dto.request.CreateRoutineRequest
import com.info.maeumgagym.routine.dto.response.RoutineListResponse
import com.info.maeumgagym.routine.port.`in`.CreateRoutineUseCase
import com.info.maeumgagym.routine.port.`in`.ReadAllMyRoutineUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/routine")
@WebAdapter
class RoutineController(
    private val createRoutineUseCase: CreateRoutineUseCase,
    private val readAllMyRoutineUseCase: ReadAllMyRoutineUseCase
) {
    @PostMapping
    fun createRoutine(@RequestBody createRoutineRequest: CreateRoutineRequest) =
        createRoutineUseCase.createRoutine(createRoutineRequest)

    @GetMapping("/all")
    fun readAllMyRoutine(): RoutineListResponse = readAllMyRoutineUseCase.readAllMyRoutine()
}
