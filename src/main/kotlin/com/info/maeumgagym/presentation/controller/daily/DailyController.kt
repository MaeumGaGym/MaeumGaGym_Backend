package com.info.maeumgagym.presentation.controller.daily

import com.info.maeumgagym.common.annotation.responsibility.WebAdapter
import com.info.maeumgagym.common.annotation.security.RequireAuthentication
import com.info.maeumgagym.core.daily.dto.response.DailyListResponse
import com.info.maeumgagym.core.daily.dto.response.PreSignedURLResponse
import com.info.maeumgagym.core.daily.port.`in`.*
import com.info.maeumgagym.presentation.common.locationheader.LocationHeaderManager
import com.info.maeumgagym.presentation.controller.daily.dto.CreateDailyRequest
import com.info.maeumgagym.presentation.controller.daily.dto.DailyTitleUpdateRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import javax.validation.Valid
import javax.validation.constraints.*

@Tag(name = "Daily Exercise Complete API")
@Validated
@WebAdapter
private class DailyController(
    private val createDailyUseCase: CreateDailyUseCase,
    private val updateDailyUseCase: UpdateDailyUseCase,
    private val readDailyUseCase: ReadDailyUseCase,
    private val deleteDailyUseCase: DeleteDailyUseCase,
    private val getDailyPreSignedURLUseCase: GetDailyPreSignedURLUseCase,
    private val locationHeaderManager: LocationHeaderManager
) {
    @Operation(description = "오운완 업로드 URL 얻기 API")
    @RequireAuthentication
    @GetMapping("/daily/pre-signed")
    fun preSignedUrl(
        @Valid
        @RequestBody
        req: CreateDailyRequest
    ): PreSignedURLResponse = getDailyPreSignedURLUseCase.getUploadUrl(req.title!!)

    @Operation(description = "오운완 생성 API")
    @ResponseStatus(HttpStatus.CREATED)
    @RequireAuthentication
    @PostMapping("/daily")
    fun dailyUpload(
        @Valid
        @RequestBody
        req: CreateDailyRequest
    ) {
        createDailyUseCase.create(req.title!!).run {
            locationHeaderManager.setSubject(subject)
        }
    }

    @Operation(description = "오운완 제목 수정 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequireAuthentication
    @PatchMapping("/daily/{date}")
    fun dailyUpdate(
        @Valid
        @NotNull(message = "null일 수 없습니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @PathVariable("date", required = false)
        date: LocalDate?,
        @Valid
        @RequestBody
        req: DailyTitleUpdateRequest
    ) {
        updateDailyUseCase.updateTitle(req.title!!, date!!)
    }

    @Operation(description = "오운완 리스트 보기 API")
    @RequireAuthentication
    @GetMapping("/dailies")
    fun dailiesRead(
        @Valid
        @PositiveOrZero(message = "0보다 크거나 같아야 합니다.")
        @RequestParam("page", required = false, defaultValue = "0")
        page: Int,
        @Valid
        @Positive(message = "0보다 커야 합니다.")
        @RequestParam("size", required = false, defaultValue = "15")
        size: Int
    ): DailyListResponse = readDailyUseCase.readDailies(page, size)

    @Operation(description = "오운완 삭제 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequireAuthentication
    @DeleteMapping("/daily/{date}")
    fun dailyDelete(
        @Valid
        @NotNull(message = "null일 수 없습니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @PathVariable("date", required = false)
        date: LocalDate?
    ) {
        deleteDailyUseCase.delete(date!!)
    }
}
