package com.info.maeumgagym.controller.pose

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.pose.dto.PoseDetailWebResponse
import com.info.maeumgagym.pose.port.`in`.ReadByIdUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Pose API")
@RequestMapping("/poses")
@WebAdapter
@Validated
class PoseController(
    private val readByIdUseCase: ReadByIdUseCase
) {

    @Operation(summary = "포즈 조회 API")
    @GetMapping
    fun readById(
        @RequestParam(required = true) id: Long?
    ): PoseDetailWebResponse = PoseDetailWebResponse.toWebResponse(readByIdUseCase.poseDetailResponseById(id!!))
}
