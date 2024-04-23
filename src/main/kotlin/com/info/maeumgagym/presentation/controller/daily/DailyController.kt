package com.info.maeumgagym.controller.daily

import com.info.maeumgagym.common.responsibility.WebAdapter
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
class DailyController(
    private val createDailyUseCase: com.info.maeumgagym.core.daily.port.`in`.CreateDailyUseCase,
    private val updateDailyUseCase: com.info.maeumgagym.core.daily.port.`in`.UpdateDailyUseCase,
    private val readDailyUseCase: com.info.maeumgagym.core.daily.port.`in`.ReadDailyUseCase,
    private val deleteDailyUseCase: com.info.maeumgagym.core.daily.port.`in`.DeleteDailyUseCase,
    private val getDailyPreSignedURLUseCase: com.info.maeumgagym.core.daily.port.`in`.GetDailyPreSignedURLUseCase,
    private val locationHeaderManager: com.info.maeumgagym.presentation.controller.common.locationheader.LocationHeaderManager
) {
    @Operation(description = "오운완 업로드 URL 얻기 API")
    @GetMapping("/daily/pre-signed")
    fun preSignedUrl(
        @Valid
        @RequestBody
        req: com.info.maeumgagym.presentation.controller.daily.dto.CreateDailyRequest
    ): com.info.maeumgagym.core.daily.dto.response.PreSignedURLResponse = getDailyPreSignedURLUseCase.getUploadUrl(req.title!!)

    @Operation(description = "오운완 생성 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/daily")
    fun dailyUpload(
        @Valid
        @RequestBody
        req: com.info.maeumgagym.presentation.controller.daily.dto.CreateDailyRequest
    ) {
        createDailyUseCase.create(req.title!!).run {
            locationHeaderManager.setSubject(subject)
        }
    }

    @Operation(description = "오운완 제목 수정 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/daily/{date}")
    fun dailyUpdate(
        @Valid
        @NotNull(message = "null일 수 없습니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @PathVariable("date", required = false)
        date: LocalDate?,
        @Valid
        @RequestBody
        req: com.info.maeumgagym.presentation.controller.daily.dto.DailyTitleUpdateRequest
    ) {
        updateDailyUseCase.updateTitle(req.title!!, date!!)
    }

    @Operation(description = "오운완 리스트 보기 API")
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
    ): com.info.maeumgagym.core.daily.dto.response.DailyListResponse = readDailyUseCase.readDailies(page, size)

    @Operation(description = "오운완 삭제 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
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
