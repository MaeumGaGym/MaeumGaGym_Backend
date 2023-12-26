package com.info.maeumgagym.controller.pickle

import com.info.common.WebAdapter
import com.info.maeumgagym.pickle.port.`in`.UpdatePickleUseCase
import com.info.maeumgagym.pickle.dto.request.PickleUploadRequest
import com.info.maeumgagym.pickle.dto.request.PreSignedUploadURLRequest
import com.info.maeumgagym.pickle.dto.request.UpdatePickleRequest
import com.info.maeumgagym.pickle.dto.response.PickleListResponse
import com.info.maeumgagym.pickle.dto.response.PreSignedUploadURLResponse
import com.info.maeumgagym.pickle.port.`in`.GetPreSignedUploadURLUseCase
import com.info.maeumgagym.pickle.port.`in`.PickleDeleteUseCase
import com.info.maeumgagym.pickle.port.`in`.PickleUploadUseCase
import com.info.maeumgagym.pickle.port.`in`.LoadRecommendationPicklesUseCase
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Validated
@WebAdapter
@RequestMapping("/pickle")
class PickleController(
    private val loadRecommendationPicklesUseCase: LoadRecommendationPicklesUseCase,
    private val pickleUploadUseCase: PickleUploadUseCase,
    private val pickleDeleteUseCase: PickleDeleteUseCase,
    private val updatePickleUseCase: UpdatePickleUseCase,
    private val getPreSignedUploadURLUseCase: GetPreSignedUploadURLUseCase
) {

    @GetMapping
    fun recommendationPicklesLoad(
        @RequestParam index: Int,
        httpServletResponse: HttpServletResponse
    ): PickleListResponse =
        loadRecommendationPicklesUseCase.loadRecommendationPickles(index)


    @GetMapping("/url")
    fun getPreSignedUploadURL(
        @RequestBody @Valid
        req: PreSignedUploadURLRequest
    ): PreSignedUploadURLResponse = getPreSignedUploadURLUseCase.getPreSignedUploadURL(req.fileType!!)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun uploadPickle(
        @RequestBody @Valid
        req: PickleUploadRequest
    ) { pickleUploadUseCase.uploadPickle(req) }

    @DeleteMapping("/{id}")
    fun deletePickle(
        @PathVariable(name = "id", required = true)
        @Valid
        @NotNull(message = "null일 수 없습니다")
        id: Long?
    ) { pickleDeleteUseCase.deletePickle(id!!) }

    @PutMapping("/{id}")
    fun updatePickle(
        @PathVariable(name = "id", required = true)
        @Valid
        @NotNull(message = "null일 수 없습니다")
        id: Long?,
        @RequestBody @Valid
        req: UpdatePickleRequest
    ) = updatePickleUseCase.updatePickle(id!!, req) // 피클 조회 완성 시 수정 후에 DTO에 담아 반환 하도록 변경하기
}
