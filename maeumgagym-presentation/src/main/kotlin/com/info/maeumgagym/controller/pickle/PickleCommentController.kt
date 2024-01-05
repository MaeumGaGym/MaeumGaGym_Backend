package com.info.maeumgagym.controller.pickle

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.pickle.dto.request.PickleCommentWebRequest
import com.info.maeumgagym.controller.pickle.dto.request.PickleUploadWebRequest
import com.info.maeumgagym.pickle.port.`in`.CreatePickleCommentUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

@Tag(name = "Pickle Comment API")
@Validated
@WebAdapter
@RequestMapping("/pickles/comment")
class PickleCommentController(
    private val createPickleCommentUseCase: CreatePickleCommentUseCase
) {

    @Operation(summary = "피클 댓글 추가 API")
    @PostMapping
    fun createPickleComment(
        @RequestBody @Valid
        req: PickleCommentWebRequest
    ) {
        createPickleCommentUseCase.createPickleComment(req.toRequest())
    }
}
