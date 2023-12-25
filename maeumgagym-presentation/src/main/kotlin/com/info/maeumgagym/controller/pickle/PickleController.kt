package com.info.maeumgagym.controller.pickle

import com.info.common.WebAdapter
import com.info.maeumgagym.pickle.dto.request.PickleUploadRequest
import com.info.maeumgagym.pickle.port.`in`.PickleUploadUseCase
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import javax.validation.Valid

@Validated
@WebAdapter
@RequestMapping("/pickle")
class PickleController(
    private val pickleUploadUseCase: PickleUploadUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun uploadPickle(
        @RequestBody @Valid
        req: PickleUploadRequest
    ) {
        pickleUploadUseCase.uploadPickle(req)
    }
}
