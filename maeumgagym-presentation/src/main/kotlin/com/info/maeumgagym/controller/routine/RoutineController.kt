package com.info.maeumgagym.controller.routine

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.common.locationheader.LocationHeaderSubjectManager
import com.info.maeumgagym.controller.routine.dto.CreateRoutineWebRequest
import com.info.maeumgagym.controller.routine.dto.UpdateRoutineWebRequest
import com.info.maeumgagym.routine.dto.response.RoutineListResponse
import com.info.maeumgagym.routine.dto.response.RoutineResponse
import com.info.maeumgagym.routine.port.`in`.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import javax.validation.constraints.Positive

@Tag(name = "Routine API")
@RequestMapping("/routines")
@WebAdapter
@Validated
class RoutineController(
    private val readTodayRoutineUseCase: ReadTodayRoutineUseCase,
    private val readAllMyRoutineUseCase: ReadAllMyRoutineUseCase,
    private val createRoutineUseCase: CreateRoutineUseCase,
    private val deleteRoutineUseCase: DeleteRoutineUseCase,
    private val updateRoutineUseCase: UpdateRoutineUseCase,
    private val readRoutineUseCase: ReadRoutineUseCase,
    private val locationHeaderSubjectManager: LocationHeaderSubjectManager,
    private val completeTodayRoutineUseCase: CompleteTodayRoutineUseCase
) {
    @Operation(summary = "루틴 생성 API")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createRoutine(
        @RequestBody @Valid
        req: CreateRoutineWebRequest
    ) {
        createRoutineUseCase.createRoutine(req.toRequest()).run {
            locationHeaderSubjectManager.setSubject(subject)
        }
    }

    @Operation(summary = "오늘의 루틴 조회 API")
    @GetMapping("/today")
    fun readTodayRoutine(httpServletResponse: HttpServletResponse): RoutineResponse? =
        readTodayRoutineUseCase.readTodayRoutine().apply {
            if (this == null) httpServletResponse.status = 204
        }

    @Operation(summary = "내 루틴 전체 조회 API")
    @GetMapping("/me/all")
    fun readAllMyRoutine(): RoutineListResponse = readAllMyRoutineUseCase.readAllMyRoutine()

    @Operation(summary = "루틴 삭제 API")
    @DeleteMapping("/{id}")
    fun deleteRoutine(
        @PathVariable("id")
        @Valid
        @Positive(message = "0보다 커야 합니다.")
        id: Long
    ) {
        deleteRoutineUseCase.deleteRoutine(id)
    }

    @Operation(summary = "루틴 수정 API")
    @PutMapping("/{id}")
    fun updateRoutine(
        @PathVariable("id")
        @Valid
        @Positive(message = "0보다 커야 합니다.")
        id: Long,
        @RequestBody @Valid
        req: UpdateRoutineWebRequest
    ) {
        updateRoutineUseCase.updateRoutine(req.toRequest(), id)
    }

    @Operation(summary = "단일 루틴 조회 API")
    @GetMapping("/{id}")
    fun readDetailRoutine(
        @PathVariable("id")
        @Valid
        @Positive(message = "0보다 커야 합니다.")
        id: Long
    ): RoutineResponse = readRoutineUseCase.readFromId(id)

    @Operation(summary = "오늘의 루틴 완료 API")
    @PutMapping("/today/complete")
    fun completeTodayRoutine() {
        completeTodayRoutineUseCase.complete()
    }
}
