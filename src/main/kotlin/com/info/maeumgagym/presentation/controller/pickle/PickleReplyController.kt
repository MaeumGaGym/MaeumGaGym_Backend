package com.info.maeumgagym.presentation.controller.pickle

import com.info.maeumgagym.common.annotation.responsibility.WebAdapter
import com.info.maeumgagym.common.annotation.security.RequireAuthentication
import com.info.maeumgagym.core.pickle.dto.response.PickleReplyListResponse
import com.info.maeumgagym.core.pickle.port.`in`.CreatePickleReplyCommentUseCase
import com.info.maeumgagym.core.pickle.port.`in`.DeletePickleReplyUseCase
import com.info.maeumgagym.core.pickle.port.`in`.LoadAllPickleReplyUseCase
import com.info.maeumgagym.presentation.common.locationheader.LocationHeaderManager
import com.info.maeumgagym.presentation.controller.pickle.dto.PickleCommentWebRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.*

@Tag(name = "Pickle Reply API")
@Validated
@WebAdapter
@RequestMapping("/pickle/replies")
private class PickleReplyController(
    private val createPickleReplyCommentUseCase: CreatePickleReplyCommentUseCase,
    private val readAllPickleReplyUseCase: LoadAllPickleReplyUseCase,
    private val deletePickleReplyUseCase: DeletePickleReplyUseCase,
    private val locationHeaderManager: LocationHeaderManager
) {
    @Operation(summary = "피클 대댓글 추가 API")
    @ResponseStatus(HttpStatus.CREATED)
    @RequireAuthentication
    @PostMapping("/{videoId}/{parentId}")
    fun createPickleReplyComment(
        @RequestBody @Valid
        req: PickleCommentWebRequest,
        @PathVariable(value = "videoId")
        @NotBlank(message = "video_id는 null일 수 없습니다.")
        @Pattern(regexp = "^[0-9a-f]{8}$")
        @Valid
        videoId: String?,
        @PathVariable(value = "parentId")
        @Valid
        @Positive(message = "0보다 커야 합니다")
        parentId: Long
    ) {
        createPickleReplyCommentUseCase
            .createPickleReplyComment(req.toRequest(), videoId!!, parentId).run {
                locationHeaderManager.setSubject(subject)
            }
    }

    @Operation(summary = "피클 대댓글 전체조회 API")
    @RequireAuthentication
    @GetMapping("/{parentId}")
    fun readAllPickleReply(
        @PathVariable(value = "parentId", required = false)
        @Valid
        @NotNull(message = "parent_id는 null일 수 없습니다.")
        @Positive(message = "0보다 커야 합니다")
        parentId: Long?,
        @RequestParam(required = false, defaultValue = "0", value = "page")
        @Valid
        @PositiveOrZero(message = "0보다 크거나 같아야 합니다.")
        page: Int,
        @RequestParam(required = false, defaultValue = "5", value = "size")
        @Valid
        @Positive(message = "0보다 커야 합니다.")
        size: Int
    ): PickleReplyListResponse =
        readAllPickleReplyUseCase.loadAllPickleReply(parentId!!, page, size)

    @Operation(summary = "피클 대댓글 삭제 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequireAuthentication
    @DeleteMapping("/{replyId}")
    fun deletePickleComment(
        @PathVariable(value = "replyId", required = false)
        @Valid
        @NotNull(message = "reply_id는 null일 수 없습니다.")
        @Positive(message = "0보다 커야 합니다")
        replyId: Long?
    ) {
        deletePickleReplyUseCase.deleteFromId(replyId!!)
    }
}
