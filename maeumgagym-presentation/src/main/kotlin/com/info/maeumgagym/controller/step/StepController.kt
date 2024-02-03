package com.info.maeumgagym.controller.step

import com.info.common.WebAdapter
import com.info.maeumgagym.step.port.`in`.CreateStepUseCase
import com.info.maeumgagym.step.port.`in`.UpdateStepUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Step API")
@Validated
@WebAdapter
@RequestMapping("/step")
class StepController(
    private val createStepUseCase: CreateStepUseCase,
    private val updateStepUseCase: UpdateStepUseCase
) {

    @Operation(summary = "걸음수 생성 API")
    @PostMapping
    fun createStep() = createStepUseCase.createStep()

    @Operation(summary = "걸음수 수정 API")
    @PutMapping
    fun updateStep(
        @RequestParam(name = "number_of_steps")
        numberOfSteps: Int
    ) = updateStepUseCase.updateStep(numberOfSteps)
}
