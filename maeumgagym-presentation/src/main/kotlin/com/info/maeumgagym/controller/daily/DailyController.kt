package com.info.maeumgagym.controller.daily

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.common.locationheader.LocationHeaderSubjectManager
import com.info.maeumgagym.controller.daily.dto.CreateDailyRequest
import com.info.maeumgagym.controller.daily.dto.DailyTitleUpdateRequest
import com.info.maeumgagym.daily.dto.response.DailyListResponse
import com.info.maeumgagym.daily.dto.response.PreSignedURLResponse
import com.info.maeumgagym.daily.port.`in`.*
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
    private val createDailyUseCase: CreateDailyUseCase,
    private val updateDailyUseCase: UpdateDailyUseCase,
    private val readDailyUseCase: ReadDailyUseCase,
    private val deleteDailyUseCase: DeleteDailyUseCase,
    private val getDailyPreSignedURLUseCase: GetDailyPreSignedURLUseCase,
    private val locationHeaderSubjectManager: LocationHeaderSubjectManager
) {
    @Operation(description = "오운완 업로드 URL 얻기 API")
    @GetMapping("/daily/pre-signed")
    fun preSignedUrl(
        @Valid
        @RequestBody
        req: CreateDailyRequest
    ): PreSignedURLResponse = getDailyPreSignedURLUseCase.getUploadUrl(req.title!!)

    @Operation(description = "오운완 생성 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/daily")
    fun dailyUpload(
        @Valid
        @RequestBody
        req: CreateDailyRequest
    ) {
        createDailyUseCase.create(req.title!!).run {
            locationHeaderSubjectManager.setSubject(subject)
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
        req: DailyTitleUpdateRequest
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
    ): DailyListResponse = readDailyUseCase.readDailies(page, size)

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
