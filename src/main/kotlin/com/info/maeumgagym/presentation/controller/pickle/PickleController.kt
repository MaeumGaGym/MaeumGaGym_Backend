package com.info.maeumgagym.presentation.controller.pickle

import com.info.maeumgagym.common.annotation.responsibility.WebAdapter
import com.info.maeumgagym.common.annotation.security.RequireAuthentication
import com.info.maeumgagym.core.pickle.dto.response.PickleListResponse
import com.info.maeumgagym.core.pickle.dto.response.PickleResponse
import com.info.maeumgagym.core.pickle.dto.response.PreSignedUploadURLResponse
import com.info.maeumgagym.core.pickle.port.`in`.*
import com.info.maeumgagym.presentation.common.locationheader.LocationHeaderManager
import com.info.maeumgagym.presentation.controller.pickle.dto.CreatePickleWebRequest
import com.info.maeumgagym.presentation.controller.pickle.dto.PreSignedUploadURLWebRequest
import com.info.maeumgagym.presentation.controller.pickle.dto.UpdatePickleWebRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.*

@Tag(name = "Pickle API")
@Validated
@WebAdapter
@RequestMapping("/pickles")
private class PickleController(
    private val loadRecommendationPicklesUseCase: LoadRecommendationPicklesUseCase,
    private val loadPickleFromIdUseCase: LoadPickleFromIdUseCase,
    private val loadPickleFromPoseUseCase: LoadPickleFromPoseUseCase,
    private val createPickleUseCase: CreatePickleUseCase,
    private val deletePickleUseCase: DeletePickleUseCase,
    private val updatePickleUseCase: UpdatePickleUseCase,
    private val getPicklePreSignedURLUseCase: GetPicklePreSignedURLUseCase,
    private val likePickleUseCase: LikePickleUseCase,
    private val locationHeaderManager: LocationHeaderManager
) {

    @Operation(summary = "추천 피클 전체 조회 API")
    @RequireAuthentication
    @GetMapping
    fun recommendationPicklesLoad(
        @RequestParam("idx", required = false)
        @Valid
        @NotNull(message = "null일 수 없습니다.")
        @PositiveOrZero(message = "0보다 크거나 같아야 합니다.")
        idx: Int?
    ): PickleListResponse =
        loadRecommendationPicklesUseCase.loadRecommendationPickles(idx!!)

    @Operation(summary = "피클 조회 API")
    @GetMapping("/{id}")
    fun pickleLoadFromId(
        @PathVariable(name = "id")
        @Valid
        @NotBlank(message = "null일 수 없습니다")
        @Pattern(regexp = "^[0-9a-f]{8}$", message = "잘못된 id입니다.")
        id: String?
    ): PickleResponse = loadPickleFromIdUseCase.loadPickleFromId(id!!)

    @Operation(summary = "자세 관련 피클 조회 API")
    @GetMapping("/pose/{poseId}")
    fun picklesLoadFromPose(
        @PathVariable(name = "poseId", required = false)
        @Valid
        @NotNull(message = "null일 수 없습니다.")
        @Positive(message = "0보다 커야 합니다.")
        poseId: Long?,
        @RequestParam("idx", required = false)
        @Valid
        @NotNull(message = "null일 수 없습니다.")
        @PositiveOrZero(message = "0보다 크거나 같아야 합니다.")
        idx: Int?,
        @RequestParam("size", required = false, defaultValue = "5")
        @Valid
        @Positive(message = "0보다 크거나 같아야 합니다.")
        size: Int
    ): PickleListResponse = loadPickleFromPoseUseCase.loadAllPagedFromPose(poseId!!, idx!!, size)

    @Operation(summary = "PreSignedUploadURL 조회 API")
    @GetMapping("/url")
    fun getPreSignedUploadURL(
        @RequestBody @Valid
        req: PreSignedUploadURLWebRequest
    ): PreSignedUploadURLResponse = getPicklePreSignedURLUseCase.getUploadURL(req.fileType!!)

    @Operation(summary = "피클 생성 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createPickle(
        @RequestBody @Valid
        req: CreatePickleWebRequest
    ) {
        createPickleUseCase.createPickle(req.toRequest()).run {
            locationHeaderManager.setSubject(subject)
        }
    }

    @Operation(summary = "피클 삭제 API")
    @DeleteMapping("/{id}")
    fun deletePickle(
        @PathVariable(name = "id")
        @Valid
        @NotBlank(message = "null일 수 없습니다")
        @Pattern(regexp = "^[0-9a-fA-F]{8}$", message = "잘못된 id입니다.")
        id: String?
    ) {
        deletePickleUseCase.deleteFromId(id!!)
    }

    @Operation(summary = "피클 수정 API")
    @PutMapping("/{id}")
    fun updatePickle(
        @PathVariable(name = "id")
        @Valid
        @NotBlank(message = "null일 수 없습니다")
        @Pattern(regexp = "^[0-9a-fA-F]{8}$", message = "잘못된 id입니다.")
        id: String?,
        @RequestBody @Valid
        req: UpdatePickleWebRequest
    ) {
        updatePickleUseCase.updatePickle(id!!, req.toRequest())
    }

    @Operation(summary = "피클 좋아요 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("{id}")
    fun likePickle(
        @PathVariable(name = "id")
        @Valid
        @NotBlank(message = "null일 수 없습니다")
        @Pattern(regexp = "^[0-9a-fA-F]{8}$", message = "잘못된 id입니다.")
        id: String?
    ) {
        likePickleUseCase.likePickle(id!!)
    }
}
