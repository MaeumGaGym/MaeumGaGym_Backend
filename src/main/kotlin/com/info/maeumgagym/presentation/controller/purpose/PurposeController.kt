package com.info.maeumgagym.presentation.controller.purpose

import com.info.maeumgagym.common.responsibility.WebAdapter
import com.info.maeumgagym.core.purpose.dto.response.PurposeInfoResponse
import com.info.maeumgagym.core.purpose.dto.response.PurposeListResponse
import com.info.maeumgagym.core.purpose.port.`in`.*
import com.info.maeumgagym.presentation.common.locationheader.LocationHeaderManager
import com.info.maeumgagym.presentation.controller.purpose.dto.CreatePurposeWebRequest
import com.info.maeumgagym.presentation.controller.purpose.dto.UpdatePurposeWebRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

@Tag(name = "Purpose APIs")
@Validated
@RequestMapping("/purposes")
@WebAdapter
private class PurposeController(
    private val createPurposeUseCase: CreatePurposeUseCase,
    private val readPurposeFromIdUseCase: ReadPurposeFromIdUseCase,
    private val readPurposesFromDateForRangeUseCase: ReadPurposesFromDateForRangeUseCase,
    private val readAllMyPurposeUseCase: ReadAllMyPurposeUseCase,
    private val updatePurposeUseCase: UpdatePurposeUseCase,
    private val deletePurposeUseCase: DeletePurposeUseCase,
    private val locationHeaderManager: LocationHeaderManager
) {

    @Operation(summary = "목표 생성 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun purposeCreate(
        @Valid
        @RequestBody
        req: CreatePurposeWebRequest
    ) {
        createPurposeUseCase.createPurpose(req.toRequest()).run {
            locationHeaderManager.setSubject(subject)
        }
    }

    @Operation(summary = "월별 목표 조회 API")
    @GetMapping("/month/{date}")
    fun purposeReadFromYearAndMonth(
        @Valid
        @NotNull(message = "null일 수 없습니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @PathVariable("date", required = false)
        date: LocalDate?
    ): PurposeListResponse =
        readPurposesFromDateForRangeUseCase.readPurposesFromDateForRange(date!!)

    @Operation(summary = "목표 id 기반 조회 API")
    @GetMapping("/{id}")
    fun purposeReadFromId(
        @Valid
        @Positive(message = "1 이상이어야 합니다.")
        @PathVariable
        id: Long
    ): PurposeInfoResponse = readPurposeFromIdUseCase.readPurposeFromId(id)

    @Operation(summary = "내 목표 전체 조회")
    @GetMapping("/my")
    fun purposeReadAllMine(
        @RequestParam
        @PositiveOrZero(message = "0 이상이어야 합니다.")
        index: Int
    ): PurposeListResponse = readAllMyPurposeUseCase.readAllMyPurpose(index)

    @Operation(summary = "목표 수정 API")
    @PutMapping("/{id}")
    fun purposeUpdate(
        @Valid
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun purposeDelete(
        @Valid
        @Positive(message = "1 이상이여야 합니다.")
        @PathVariable
        id: Long
    ) {
        deletePurposeUseCase.deletePurposeFromId(id)
    }
}
