package com.info.maeumgagym.controller.routine

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.routine.dto.request.CreateRoutineWebRequest
import com.info.maeumgagym.controller.routine.dto.response.RoutineWebListResponse
import com.info.maeumgagym.controller.routine.dto.request.UpdateRoutineWebRequest
import com.info.maeumgagym.routine.port.`in`.CreateRoutineUseCase
import com.info.maeumgagym.routine.port.`in`.DeleteRoutineUseCase
import com.info.maeumgagym.routine.port.`in`.ReadAllMyRoutineUseCase
import com.info.maeumgagym.routine.port.`in`.UpdateRoutineUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotNull

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
    fun readAllMyRoutine(): RoutineWebListResponse = RoutineWebListResponse.toWebResponse(
        readAllMyRoutineUseCase.readAllMyRoutine()
    )

    @Operation(summary = "루틴 삭제 API")
    @DeleteMapping("/{routineId}")
    fun deleteRoutine(
        @PathVariable(required = true)
        @Valid
        @NotNull(message = "null일 수 없습니다.")
        routineId: Long?
    ) =
        deleteRoutineUseCase.deleteRoutine(routineId!!)

    @Operation(summary = "루틴 수정 API")
    @PutMapping("/{id}")
    fun updateRoutine(
        @PathVariable(required = true)
        @Valid
        @NotNull(message = "null일 수 없습니다.")
        id: Long?,
        @RequestBody @Valid
        req: UpdateRoutineWebRequest
    ) =
        updateRoutineUseCase.updateRoutine(req.toRequest(), id!!)
}
