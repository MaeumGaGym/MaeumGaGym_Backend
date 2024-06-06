package com.info.maeumgagym.presentation.controller.routine

import com.info.maeumgagym.common.annotation.responsibility.WebAdapter
import com.info.maeumgagym.common.annotation.security.RequireAuthentication
import com.info.maeumgagym.core.routine.dto.response.CompletableRoutineListResponse
import com.info.maeumgagym.core.routine.dto.response.RoutineHistoryListResponse
import com.info.maeumgagym.core.routine.dto.response.RoutineListResponse
import com.info.maeumgagym.core.routine.dto.response.RoutineResponse
import com.info.maeumgagym.core.routine.port.`in`.*
import com.info.maeumgagym.presentation.common.locationheader.LocationHeaderManager
import com.info.maeumgagym.presentation.controller.routine.dto.CreateRoutineWebRequest
import com.info.maeumgagym.presentation.controller.routine.dto.UpdateRoutineWebRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

@Tag(name = "Routine API")
@RequestMapping("/routines")
@WebAdapter
@Validated
private class RoutineController(
    private val readTodayRoutineUseCase: ReadTodayRoutineUseCase,
    private val readAllMyRoutineUseCase: ReadAllMyRoutineUseCase,
    private val createRoutineUseCase: CreateRoutineUseCase,
    private val deleteRoutineUseCase: DeleteRoutineUseCase,
    private val updateRoutineUseCase: UpdateRoutineUseCase,
    private val readRoutineUseCase: ReadRoutineUseCase,
    private val locationHeaderManager: LocationHeaderManager,
    private val completeRoutineUseCase: CompleteRoutineUseCase,
    private val incompleteRoutineUseCase: IncompleteRoutineUseCase,
    private val readRoutineHistoryUseCase: ReadRoutineHistoryUseCase
) {
    @Operation(summary = "루틴 생성 API")
    @ResponseStatus(HttpStatus.CREATED)
    @RequireAuthentication
    @PostMapping
    fun createRoutine(
        @RequestBody @Valid
        req: CreateRoutineWebRequest
    ) {
        createRoutineUseCase.createRoutine(req.toRequest()).run {
            locationHeaderManager.setSubject(subject)
        }
    }

    @Operation(summary = "오늘의 루틴 조회 API")
    @RequireAuthentication
    @GetMapping("/today")
    fun readTodayRoutine(): CompletableRoutineListResponse =
        readTodayRoutineUseCase.readTodayRoutine()

    @Operation(summary = "내 루틴 전체 조회 API")
    @RequireAuthentication
    @GetMapping("/my")
    fun readAllMyRoutine(
        @RequestParam
        @PositiveOrZero(message = "0 이상이어야 합니다.")
        index: Int
    ): RoutineListResponse = readAllMyRoutineUseCase.readAllMyRoutine(index)

    @Operation(summary = "루틴 삭제 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequireAuthentication
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
    @RequireAuthentication
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
    @RequireAuthentication
    @GetMapping("/{id}")
    fun readDetailRoutine(
        @PathVariable("id")
        @Valid
        @Positive(message = "0보다 커야 합니다.")
        id: Long
    ): RoutineResponse = readRoutineUseCase.readFromId(id)

    @Operation(summary = "루틴 완료 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequireAuthentication
    @PutMapping("/today/complete/{id}")
    fun completeTodayRoutine(
        @PathVariable
        @Valid
        @Positive(message = "0보다 커야 합니다.")
        id: Long
    ) {
        completeRoutineUseCase.completeFromId(id)
    }

    @Operation(summary = "루틴 미완료 처리 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequireAuthentication
    @PutMapping("/incomplete/{date}/{id}")
    fun incompleteTodayRoutine(
        @PathVariable
        @Valid
        @Positive(message = "0보다 커야 합니다.")
        id: Long,
        @Valid
        @NotNull(message = "null일 수 없습니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @PathVariable("date", required = false)
        date: LocalDate?
    ) {
        incompleteRoutineUseCase.incompleteRoutineFromOriginRoutineIdAndDate(id, date!!)
    }

    @Operation(summary = "루틴 기록 조회 API")
    @RequireAuthentication
    @GetMapping("/histories/{date}")
    fun readHistories(
        @Valid
        @NotNull(message = "null일 수 없습니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @PathVariable("date", required = false)
        date: LocalDate?
    ): RoutineHistoryListResponse = readRoutineHistoryUseCase.readFromDate(date!!)
}
