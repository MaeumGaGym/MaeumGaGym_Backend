package com.info.maeumgagym.controller.step

import com.info.maeumgagym.common.responsibility.WebAdapter
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Tag(name = "Step API")
@Validated
@WebAdapter
@RequestMapping("/step")
class StepController(
    private val updateStepUseCase: com.info.maeumgagym.core.step.port.`in`.UpdateStepUseCase,
    private val readTodayStepCountUseCase: com.info.maeumgagym.core.step.port.`in`.ReadTodayStepCountUseCase
) {
    @Operation(summary = "걸음 수 카운트 업데이트 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    fun updateStep(
        @RequestParam(name = "number_of_steps")
        numberOfSteps: Int
    ) = updateStepUseCase.updateStep(numberOfSteps)

    @Operation(summary = "오늘 걸음 수 조회 API")
    @GetMapping
    fun readTodayStepCount() = readTodayStepCountUseCase.readTodayStepCount()
}
