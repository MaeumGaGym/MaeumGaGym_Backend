package com.info.maeumgagym.controller.report

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.report.dto.ReportWebRequest
import com.info.maeumgagym.report.port.`in`.ReportPickleCommentUseCase
import com.info.maeumgagym.report.port.`in`.ReportPickleReplyUseCase
import com.info.maeumgagym.report.port.`in`.ReportPickleUseCase
import com.info.maeumgagym.report.port.`in`.ReportUserUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import javax.validation.Valid
import javax.validation.constraints.Pattern

@Tag(name = "Report API")
@Validated
@RequestMapping("/report")
@WebAdapter
class ReportController(
    private val reportUserUseCase: ReportUserUseCase,
    private val reportPickleUseCase: ReportPickleUseCase,
    private val reportPickleCommentUseCase: ReportPickleCommentUseCase,
    private val reportPickleReplyUseCase: ReportPickleReplyUseCase
) {

    @Operation(summary = "유저 신고 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user/{nickname}")
    fun userReport(
        @Valid
        @RequestBody
        request: ReportWebRequest,
        @PathVariable
        nickname: String
    ) {
        reportUserUseCase.reportUser(request.toRequest(), nickname)
    }

    @Operation(summary = "피클 신고 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/pickle/{videoId}")
    fun pickleReport(
        @Valid
        @RequestBody
        request: ReportWebRequest,
        @Pattern(regexp = "^[0-9a-f]{8}\$", message = "잘못된 id입니다.")
        @PathVariable
        videoId: String
    ) {
        reportPickleUseCase.reportPickle(request.toRequest(), videoId)
    }

    @Operation(summary = "피클 댓글 신고 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/pickle-comment/{id}")
    fun pickleCommentReport(
        @Valid
        @RequestBody
        request: ReportWebRequest,
        @PathVariable
        id: Long
    ) {
        reportPickleCommentUseCase.reportPickleComment(request.toRequest(), id)
    }

    @Operation(summary = "피클 대댓글 신고 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/pickle-reply/{id}")
    fun pickleReplyReport(
        @Valid
        @RequestBody
        request: ReportWebRequest,
        @PathVariable
        id: Long
    ) {
        reportPickleReplyUseCase.reportPickleReply(request.toRequest(), id)
    }
}
