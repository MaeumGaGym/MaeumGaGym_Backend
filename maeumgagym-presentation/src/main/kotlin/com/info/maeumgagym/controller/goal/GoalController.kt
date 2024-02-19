package com.info.maeumgagym.controller.goal

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.goal.dto.CreateGoalWebRequest
import com.info.maeumgagym.goal.port.`in`.CreateGoalUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

@Tag(name = "Goal APIs")
@Validated
@RequestMapping("/goal")
@WebAdapter
class GoalController(
    private val createGoalUseCase: CreateGoalUseCase
) {

    @Operation(summary = "목표 생성 API")
    @PostMapping
    fun goalCreate(
        @Valid
        @RequestBody
        req: CreateGoalWebRequest
    ) {
        createGoalUseCase.createGoal(req.toRequest())
    }
}
