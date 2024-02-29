package com.info.maeumgagym.controller.purpose

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.purpose.dto.CreatePurposeWebRequest
import com.info.maeumgagym.controller.purpose.dto.UpdatePurposeWebRequest
import com.info.maeumgagym.purpose.dto.response.PurposeInfoResponse
import com.info.maeumgagym.purpose.dto.response.PurposeListResponse
import com.info.maeumgagym.purpose.port.`in`.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Positive

@Tag(name = "Purpose APIs")
@Validated
@RequestMapping("/purposes")
@WebAdapter
class PurposeController(
    private val createPurposeUseCase: CreatePurposeUseCase,
    private val readPurposeFromIdUseCase: ReadPurposeFromIdUseCase,
    private val readPurposesFromYearAndMonthUseCase: ReadPurposesFromYearAndMonthUseCase,
    private val updatePurposeUseCase: UpdatePurposeUseCase,
    private val deletePurposeUseCase: DeletePurposeUseCase
) {

    @Operation(summary = "목표 생성 API")
    @PostMapping
    fun purposeCreate(
        @Valid
        @RequestBody
        req: CreatePurposeWebRequest
    ) {
        createPurposeUseCase.createPurpose(req.toRequest())
    }

    @Operation(summary = "월별 목표(저번 달 ~ 다음 달) 조회 API")
    @GetMapping
    fun purposeReadFromYearAndMonth(
        @Min(value = 1000, message = "1000 이상이어야 합니다.")
        @Max(value = 3000, message = "3000 이하여야 합니다.")
        @RequestParam("year")
        year: Int,

        @Positive(message = "1 이상이어야 합니다.")
        @Max(value = 12, message = "12 이하여야 합니다.")
        @RequestParam("month")
        month: Int
    ): PurposeListResponse =
        readPurposesFromYearAndMonthUseCase.readPurposesFromYearAndMonth(year, month)

    @Operation(summary = "목표 id 기반 조회 API")
    @GetMapping("/{id}")
    fun purposeReadFromId(
        @Positive(message = "1 이상이어야 합니다.")
        @PathVariable
        id: Long
    ): PurposeInfoResponse =
        readPurposeFromIdUseCase.readPurposeFromId(id)

    @Operation(summary = "목표 수정 API")
    @PutMapping("/{id}")
    fun purposeUpdate(
        @Positive(message = "1 이상이여야 합니다.")
        @PathVariable
        id: Long,
        @Valid
        @RequestBody
        req: UpdatePurposeWebRequest
    ) {
        updatePurposeUseCase.updatePurposeFromId(id, req.toRequest())
    }

    @Operation(summary = "목표 삭제 API")
    @DeleteMapping("/{id}")
    fun purposeDelete(
        @Positive(message = "1 이상이여야 합니다.")
        @PathVariable
        id: Long
    ) {
        deletePurposeUseCase.deletePurposeFromId(id)
    }
}
