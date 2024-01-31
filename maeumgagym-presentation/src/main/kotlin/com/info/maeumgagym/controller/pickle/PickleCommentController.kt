package com.info.maeumgagym.controller.pickle

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.pickle.dto.PickleCommentWebRequest
import com.info.maeumgagym.pickle.dto.response.PickleCommentListResponse
import com.info.maeumgagym.pickle.port.`in`.CreatePickleCommentUseCase
import com.info.maeumgagym.pickle.port.`in`.DeletePickleCommentUseCase
import com.info.maeumgagym.pickle.port.`in`.ReadAllPagedPickleCommentUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

@Tag(name = "Pickle Comment API")
@Validated
@WebAdapter
@RequestMapping("/pickle/comments")
class PickleCommentController(
    private val createPickleCommentUseCase: CreatePickleCommentUseCase,
    private val readAllPagedPickleCommentUseCase: ReadAllPagedPickleCommentUseCase,
    private val deletePickleCommentUseCase: DeletePickleCommentUseCase
) {
    @Operation(summary = "피클 댓글 추가 API")
    @PostMapping("/{videoId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun createPickleComment(
        @RequestBody @Valid
        req: PickleCommentWebRequest,
        @PathVariable
        @NotBlank(message = "videoId는 null일 수 없습니다.")
        @Pattern(regexp = "^[0-9a-f]{8}$")
        @Valid
        videoId: String?
    ) {
        createPickleCommentUseCase.createPickleComment(req.toRequest(), videoId!!)
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
