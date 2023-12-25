package com.info.maeumgagym.controller.pickle

import com.info.common.WebAdapter
import com.info.maeumgagym.pickle.dto.request.PickleUploadRequest
import com.info.maeumgagym.pickle.port.`in`.PicklePutDownUseCase
import com.info.maeumgagym.pickle.port.`in`.PickleUploadUseCase
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Validated
@WebAdapter
@RequestMapping("/pickle")
class PickleController(
    private val pickleUploadUseCase: PickleUploadUseCase,
    private val picklePutDownUseCase: PicklePutDownUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun uploadPickle(
        @RequestBody @Valid
        req: PickleUploadRequest
    ) { pickleUploadUseCase.uploadPickle(req) }

    @DeleteMapping
    fun putDownPickle(
        @RequestParam(required = true) @Valid @NotNull(message = "null일 수 없습니다")
        id: Long?
    ) { picklePutDownUseCase.putDownPickleById(id!!) }
}
