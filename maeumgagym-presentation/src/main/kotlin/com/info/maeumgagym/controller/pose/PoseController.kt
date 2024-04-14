package com.info.maeumgagym.controller.pose

import com.info.common.responsibility.WebAdapter
import com.info.common.security.RequireRole
import com.info.maeumgagym.controller.common.locationheader.LocationHeaderManager
import com.info.maeumgagym.controller.pose.dto.request.CreatePoseWebRequest
import com.info.maeumgagym.controller.pose.dto.request.ReadAllPoseWebRequest
import com.info.maeumgagym.pose.dto.response.PoseDetailResponse
import com.info.maeumgagym.pose.dto.response.PoseListResponse
import com.info.maeumgagym.pose.dto.response.PoseRecommendationListResponse
import com.info.maeumgagym.pose.port.`in`.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

@Tag(name = "Pose API")
@RequestMapping("/poses")
@WebAdapter
@Validated
class PoseController(
    private val createPoseUseCase: CreatePoseUseCase,
    private val readAllPoseUseCase: ReadAllPoseUseCase,
    private val readPoseFromIdUseCase: ReadPoseFromIdUseCase,
    private val readPoseFromTagUseCase: ReadPoseFromTagUseCase,
    private val readPosesRecommendationUseCase: ReadPosesRecommendationUseCase,
    private val locationHeaderManager: LocationHeaderManager
) {

    @Operation(summary = "자세 추가 API")
    @ResponseStatus(HttpStatus.CREATED)
    @RequireRole(role = "ADMIN")
    @PostMapping
    fun create(
        @Valid
        @RequestBody
        req: CreatePoseWebRequest
    ) {
        val subject = createPoseUseCase.createPose(req.toRequest())

        locationHeaderManager.setSubject(subject.subject)
    }

    @Operation(summary = "모든 자세 목록 조회 API")
    @GetMapping("/all")
    fun readAll(
        @RequestBody
        @Valid
        readAllPoseWebRequest: ReadAllPoseWebRequest
    ): PoseListResponse = readAllPoseUseCase.readAll(readAllPoseWebRequest.toRequest())

    @Operation(summary = "자세 id 조회 API")
    @GetMapping("/{id}")
    fun readFromId(
        @PathVariable("id")
        @Valid
        @Positive(message = "0보다 커야 합니다.")
        id: Long
    ): PoseDetailResponse = readPoseFromIdUseCase.detailResponseById(id)

    @Operation(summary = "자세 태그 조회 API")
    @GetMapping("/tag")
    fun readFromTag(
        @RequestParam
        @Size(min = 1, max = 20)
        @NotBlank
        @Valid
        tag: String
    ): PoseListResponse =
        readPoseFromTagUseCase.readFromTag(tag)

    @Operation(summary = "자세 추천 조회 API")
    @GetMapping
    fun readRecommendation(): PoseRecommendationListResponse =
        readPosesRecommendationUseCase.readRecommendation()
}
