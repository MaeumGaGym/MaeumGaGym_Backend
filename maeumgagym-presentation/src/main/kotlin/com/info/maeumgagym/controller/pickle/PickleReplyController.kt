package com.info.maeumgagym.controller.pickle

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.pickle.dto.PickleCommentWebRequest
import com.info.maeumgagym.pickle.dto.response.PickleReplyListResponse
import com.info.maeumgagym.pickle.port.`in`.CreatePickleReplyCommentUseCase
import com.info.maeumgagym.pickle.port.`in`.DeletePickleReplyUseCase
import com.info.maeumgagym.pickle.port.`in`.LoadAllPickleReplyUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

@Tag(name = "Pickle Reply API")
@Validated
@WebAdapter
@RequestMapping("/pickle/replies")
class PickleReplyController(
    private val createPickleReplyCommentUseCase: CreatePickleReplyCommentUseCase,
    private val readAllPickleReplyUseCase: LoadAllPickleReplyUseCase,
    private val deletePickleReplyUseCase: DeletePickleReplyUseCase
) {
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

    @Operation(summary = "피클 대댓글 전체조회 API")
    @GetMapping("/{parentId}")
    fun readAllPickleReply(
        @PathVariable(value = "parentId")
        parentId: Long,
        @RequestParam(required = false, defaultValue = "0", value = "page") page: Int,
        @RequestParam(required = false, defaultValue = "5", value = "size") size: Int
    ): PickleReplyListResponse =
        readAllPickleReplyUseCase.loadAllPickleReply(parentId, page, size)

    @Operation(summary = "피클 대댓글 삭제 API")
    @DeleteMapping("/{replyId}")
    fun deletePickleComment(
        @PathVariable(value = "replyId")
        replyId: Long
    ) {
        deletePickleReplyUseCase.deleteFromId(replyId)
    }
}
