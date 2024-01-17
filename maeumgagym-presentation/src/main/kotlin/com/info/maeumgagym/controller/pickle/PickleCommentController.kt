package com.info.maeumgagym.controller.pickle

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.pickle.dto.PickleCommentWebRequest
import com.info.maeumgagym.pickle.dto.response.PickleCommentListResponse
import com.info.maeumgagym.pickle.port.`in`.CreatePickleCommentUseCase
import com.info.maeumgagym.pickle.port.`in`.CreatePickleReplyCommentUseCase
import com.info.maeumgagym.pickle.port.`in`.ReadAllPickleCommentsUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

@Tag(name = "Pickle Comment API")
@Validated
@WebAdapter
@RequestMapping("/pickle/comments")
class PickleCommentController(
    private val createPickleCommentUseCase: CreatePickleCommentUseCase,
    private val createPickleReplyCommentUseCase: CreatePickleReplyCommentUseCase,
    private val readAllPickleCommentsUseCase: ReadAllPickleCommentsUseCase
) {
    @Operation(summary = "피클 댓글 추가 API")
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
        createPickleCommentUseCase.createPickleComment(req.toRequest(), videoId!!)
    }

    @Operation(summary = "피클 대댓글 추가 API")
    @PostMapping("/{videoId}/{parentId}")
    fun createPickleReplyComment(
        @RequestBody @Valid
        req: PickleCommentWebRequest,
        @PathVariable(value = "videoId")
        @NotBlank(message = "videoId는 null일 수 없습니다.")
        @Pattern(regexp = "^[0-9a-f]{8}$")
        @Valid
        videoId: String?,
        @PathVariable(value = "parentId")
        parentId: Long
    ) {
        createPickleReplyCommentUseCase.createPickleReplyComment(req.toRequest(), videoId!!, parentId)
    }

    @Operation(summary = "피클 댓글 전체조회 API")
    @GetMapping("/{videoId}")
    fun readPickleComment(
        @PathVariable(value = "videoId")
        @NotBlank(message = "videoId는 null일 수 없습니다.")
        @Pattern(regexp = "^[0-9a-f]{8}$")
        @Valid
        videoId: String?
    ): PickleCommentListResponse =
        readAllPickleCommentsUseCase.readPickleCommentByVideoId(videoId!!)
}
