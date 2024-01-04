package com.info.maeumgagym.controller.routine

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.routine.dto.CreateRoutineWebRequest
import com.info.maeumgagym.controller.routine.dto.UpdateRoutineWebRequest
import com.info.maeumgagym.routine.dto.response.RoutineListResponse
import com.info.maeumgagym.routine.port.`in`.CreateRoutineUseCase
import com.info.maeumgagym.routine.port.`in`.DeleteRoutineUseCase
import com.info.maeumgagym.routine.port.`in`.ReadAllMyRoutineUseCase
import com.info.maeumgagym.routine.port.`in`.UpdateRoutineUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "Routine API")
@RequestMapping("/routines")
@WebAdapter
@Validated
class RoutineController(
    private val createRoutineUseCase: CreateRoutineUseCase,
    private val readAllMyRoutineUseCase: ReadAllMyRoutineUseCase,
    private val deleteRoutineUseCase: DeleteRoutineUseCase,
    private val updateRoutineUseCase: UpdateRoutineUseCase
) {
    @Operation(summary = "루틴 생성 API")
    @PostMapping
    fun createRoutine(
        @RequestBody @Valid
        req: CreateRoutineWebRequest
    ) {
        createRoutineUseCase.createRoutine(req.toRequest())
    }

    @Operation(summary = "내 루틴 전체 조회 API")
    @GetMapping("/all")
    fun readAllMyRoutine(): RoutineListResponse = readAllMyRoutineUseCase.readAllMyRoutine()

    @Operation(summary = "루틴 삭제 API")
    @DeleteMapping("/{id}")
    fun deleteRoutine(
        @PathVariable("id")
        id: Long
    ) { deleteRoutineUseCase.deleteRoutine(id) }

    @Operation(summary = "루틴 수정 API")
    @PutMapping("/{id}")
    fun updateRoutine(
        @PathVariable("id")
        id: Long,
        @RequestBody @Valid
        req: UpdateRoutineWebRequest
    ) { updateRoutineUseCase.updateRoutine(req.toRequest(), id) }
}
