package com.info.maeumgagym.controller.pickle

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.pickle.dto.response.PickleWebResponse
import com.info.maeumgagym.controller.pickle.dto.request.PickleUploadWebRequest
import com.info.maeumgagym.controller.pickle.dto.request.PreSignedUploadURLWebRequest
import com.info.maeumgagym.controller.pickle.dto.request.UpdatePickleWebRequest
import com.info.maeumgagym.controller.pickle.dto.response.PreSignedUploadURLWebResponse
import com.info.maeumgagym.pickle.dto.response.PickleListResponse
import com.info.maeumgagym.pickle.port.`in`.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Tag(name = "Pickle API")
@Validated
@WebAdapter
@RequestMapping("/pickles")
class PickleController(
    private val loadRecommendationPicklesUseCase: LoadRecommendationPicklesUseCase,
    private val loadPickleFromIdUseCase: LoadPickleFromIdUseCase,
    private val pickleUploadUseCase: PickleUploadUseCase,
    private val pickleDeleteUseCase: PickleDeleteUseCase,
    private val updatePickleUseCase: UpdatePickleUseCase,
    private val getPreSignedUploadURLUseCase: GetPreSignedUploadURLUseCase
) {

    @Operation(summary = "추천 피클 전체 조회 API")
    @GetMapping
    fun recommendationPicklesLoad(
        @RequestParam index: Int,
        httpServletResponse: HttpServletResponse
    ): PickleListResponse =
        loadRecommendationPicklesUseCase.loadRecommendationPickles(index)

    @Operation(summary = "피클 조회 API")
    @GetMapping("/{id}")
    fun pickleLoadFromId(
        @PathVariable(name = "id", required = true)
        @Valid
        @NotNull(message = "null일 수 없습니다.")
        id: Long?
    ): PickleWebResponse =
        PickleWebResponse.toWebResponse(
            loadPickleFromIdUseCase.loadPickleFromId(id!!)
        )

    @Operation(summary = "PreSignedUploadURL 조회 API")
    @GetMapping("/url")
    fun getPreSignedUploadURL(
        @RequestBody @Valid
        req: PreSignedUploadURLWebRequest
    ) = PreSignedUploadURLWebResponse(getPreSignedUploadURLUseCase.getPreSignedUploadURL(req.fileType!!))

    @Operation(summary = "피클 업로드 API")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun uploadPickle(
        @RequestBody @Valid
        req: PickleUploadWebRequest
    ) {
        pickleUploadUseCase.uploadPickle(req.toRequest())
    }

    @Operation(summary = "피클 삭제 API")
    @DeleteMapping("/{id}")
    fun deletePickle(
        @PathVariable(name = "id", required = true)
        @Valid
        @NotNull(message = "null일 수 없습니다")
        id: Long?
    ) {
        pickleDeleteUseCase.deletePickle(id!!)
    }

    @Operation(summary = "피클 수정 API")
    @PutMapping("/{id}")
    fun updatePickle(
        @PathVariable(name = "id", required = true)
        @Valid
        @NotNull(message = "null일 수 없습니다")
        id: Long?,
        @RequestBody @Valid
        req: UpdatePickleWebRequest
    ) = updatePickleUseCase.updatePickle(id!!, req.toRequest()) // 피클 조회 완성 시 수정 후에 DTO에 담아 반환 하도록 변경하기
}
