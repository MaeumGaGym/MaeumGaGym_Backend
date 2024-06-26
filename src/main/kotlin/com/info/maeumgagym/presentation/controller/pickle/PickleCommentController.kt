package com.info.maeumgagym.presentation.controller.pickle

import com.info.maeumgagym.common.annotation.responsibility.WebAdapter
import com.info.maeumgagym.common.annotation.security.RequireAuthentication
import com.info.maeumgagym.core.pickle.dto.response.PickleCommentListResponse
import com.info.maeumgagym.core.pickle.port.`in`.CreatePickleCommentUseCase
import com.info.maeumgagym.core.pickle.port.`in`.DeletePickleCommentUseCase
import com.info.maeumgagym.core.pickle.port.`in`.ReadAllPagedPickleCommentUseCase
import com.info.maeumgagym.presentation.common.locationheader.LocationHeaderManager
import com.info.maeumgagym.presentation.controller.pickle.dto.PickleCommentWebRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.*

@Tag(name = "Pickle Comment API")
@Validated
@WebAdapter
@RequestMapping("/pickle/comments")
private class PickleCommentController(
    private val createPickleCommentUseCase: CreatePickleCommentUseCase,
    private val readAllPagedPickleCommentUseCase: ReadAllPagedPickleCommentUseCase,
    private val deletePickleCommentUseCase: DeletePickleCommentUseCase,
    private val locationHeaderManager: LocationHeaderManager
) {
    @Operation(summary = "피클 댓글 추가 API")
    @ResponseStatus(HttpStatus.CREATED)
    @RequireAuthentication
    @PostMapping("/{videoId}")
    fun createPickleComment(
        @RequestBody @Valid
        req: PickleCommentWebRequest,
        @PathVariable
        @NotBlank(message = "videoId는 null일 수 없습니다.")
        @Pattern(regexp = "^[0-9a-f]{8}$")
        @Valid
        videoId: String?
    ) {
        createPickleCommentUseCase.createPickleComment(req.toRequest(), videoId!!).run {
            locationHeaderManager.setSubject(subject)
        }
    }

    @Operation(summary = "피클 댓글 전체조회 API")
    @GetMapping("/{videoId}")
    fun readPickleComment(
        @PathVariable(value = "videoId")
        @NotBlank(message = "videoId는 null일 수 없습니다.")
        @Pattern(regexp = "^[0-9a-f]{8}$")
        @Valid
        videoId: String?,
        @RequestParam(required = false, defaultValue = "0", value = "page")
        @Valid
        @PositiveOrZero(message = "0보다 크거나 같아야 합니다.")
        page: Int,
        @RequestParam(required = false, defaultValue = "5", value = "size")
        @Valid
        @Positive(message = "0보다 커야 합니다.")
        size: Int
    ): PickleCommentListResponse =
        readAllPagedPickleCommentUseCase.readAllPagedPickleCommentByVideoId(videoId!!, page, size)

    @Operation(summary = "피클 댓글 삭제 API")
    @RequireAuthentication
    @DeleteMapping("/{commentId}")
    fun deletePickleComment(
        @PathVariable(value = "commentId", required = false)
        @Valid
        @NotNull(message = "null일 수 없습니다.")
        @Positive(message = "0보다 커야 합니다.")
        commentId: Long?
    ) {
        deletePickleCommentUseCase.deleteFromId(commentId!!)
    }
}
