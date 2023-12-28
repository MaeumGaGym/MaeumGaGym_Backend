package com.info.maeumgagym.controller.routine

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.routine.dto.CreateRoutineWebRequest
import com.info.maeumgagym.controller.routine.dto.RoutineWebListResponse
import com.info.maeumgagym.routine.port.`in`.CreateRoutineUseCase
import com.info.maeumgagym.routine.port.`in`.DeleteRoutineUseCase
import com.info.maeumgagym.routine.port.`in`.ReadAllMyRoutineUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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
    private val deleteRoutineUseCase: DeleteRoutineUseCase
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
        routineId: UUID?
    ) =
        deleteRoutineUseCase.deleteRoutine(routineId!!)
}
