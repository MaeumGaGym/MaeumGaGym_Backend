package com.info.maeumgagym.controller.pose

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.pose.dto.PoseDetailWebResponse
import com.info.maeumgagym.pose.port.`in`.ReadByIdUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/poses")
@WebAdapter
@RestController
@Validated
class PoseController(
    private val readByIdUseCase: ReadByIdUseCase
) {

    @GetMapping
    fun readById(
        @RequestParam(required = true) id: Long?
    ): PoseDetailWebResponse = PoseDetailWebResponse.toWebResponse(readByIdUseCase.poseDetailResponseById(id!!))
}
