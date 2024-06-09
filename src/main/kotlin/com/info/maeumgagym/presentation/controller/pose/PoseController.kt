package com.info.maeumgagym.presentation.controller.pose

import com.info.maeumgagym.common.annotation.responsibility.WebAdapter
import com.info.maeumgagym.common.annotation.security.RequireAuthentication
import com.info.maeumgagym.common.annotation.security.RequireRole
import com.info.maeumgagym.core.pose.dto.response.PoseDetailResponse
import com.info.maeumgagym.core.pose.dto.response.PoseListResponse
import com.info.maeumgagym.core.pose.dto.response.PoseRecommendationListResponse
import com.info.maeumgagym.core.pose.port.`in`.*
import com.info.maeumgagym.core.user.model.Role
import com.info.maeumgagym.presentation.common.locationheader.LocationHeaderManager
import com.info.maeumgagym.presentation.controller.pose.dto.request.CreatePoseWebRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

@Tag(name = "Pose API")
@RequestMapping("/poses")
@WebAdapter
@Validated
private class PoseController(
    private val createPoseUseCase: CreatePoseUseCase,
    private val readAllPoseUseCase: ReadAllPoseUseCase,
    private val readPoseFromIdUseCase: ReadPoseFromIdUseCase,
    private val readPoseFromTagUseCase: ReadPoseFromTagUseCase,
    private val readPosesRecommendationUseCase: ReadPosesRecommendationUseCase,
    private val locationHeaderManager: LocationHeaderManager
) {

    @Operation(summary = "자세 추가 API")
    @ResponseStatus(HttpStatus.CREATED)
    @RequireRole(Role.ADMIN)
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
    @RequireAuthentication
    @GetMapping("/all")
    fun readAll(
        @Valid
        @RequestParam(value = "last_updated")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        lastUpdated: LocalDateTime?
    ): PoseListResponse = readAllPoseUseCase.readAll(lastUpdated)

    @Operation(summary = "자세 id 조회 API")
    @RequireAuthentication
    @GetMapping("/{id}")
    fun readFromId(
        @PathVariable("id")
        @Valid
        @Positive(message = "0보다 커야 합니다.")
        id: Long
    ): PoseDetailResponse = readPoseFromIdUseCase.detailResponseById(id)

    @Operation(summary = "자세 태그 조회 API")
    @RequireAuthentication
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
    @RequireAuthentication
    @GetMapping
    fun readRecommendation(): PoseRecommendationListResponse =
        readPosesRecommendationUseCase.readRecommendation()
}
