package com.info.maeumgagym.controller.pose

import com.info.common.WebAdapter
import com.info.maeumgagym.pose.dto.response.PoseDetailResponse
import com.info.maeumgagym.pose.dto.response.PoseListResponse
import com.info.maeumgagym.pose.port.`in`.ReadPoseFromIdUseCase
import com.info.maeumgagym.pose.port.`in`.ReadPoseFromTagUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

@Tag(name = "Pose API")
@RequestMapping("/poses")
@WebAdapter
@Validated
class PoseController(
    private val readPoseFromIdUseCase: ReadPoseFromIdUseCase,
    private val readPoseFromTagUseCase: ReadPoseFromTagUseCase
) {

    @Operation(summary = "자세 id 조회 API")
    @GetMapping("/{id}")
    fun readFromId(
        @PathVariable("id")
        @Valid
        @Positive(message = "0보다 커야 합니다.")
        id: Long
    ): PoseDetailResponse = readPoseFromIdUseCase.detailResponseById(id)

    @Operation(summary = "자세 태그 조회 API")
    @GetMapping
    fun readFromTag(
        @RequestParam
        @Size(min = 1, max = 20)
        @NotBlank
        @Valid
        tag: String
    ): PoseListResponse =
        readPoseFromTagUseCase.readFromTag(tag)
}
