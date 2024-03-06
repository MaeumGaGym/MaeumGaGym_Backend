package com.info.maeumgagym.controller.daily

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.daily.dto.CreateDailyRequest
import com.info.maeumgagym.daily.dto.response.DailyListResponse
import com.info.maeumgagym.daily.dto.response.PreSignedURLResponse
import com.info.maeumgagym.daily.port.`in`.CreateDailyUseCase
import com.info.maeumgagym.daily.port.`in`.DeleteDailyUseCase
import com.info.maeumgagym.daily.port.`in`.GetDailyPreSignedURLUseCase
import com.info.maeumgagym.daily.port.`in`.ReadDailyUseCase
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

@Tag(name = "Daily Exercise Complete API")
@Validated
@WebAdapter
class DailyController(
    private val createDailyUseCase: CreateDailyUseCase,
    private val readDailyUseCase: ReadDailyUseCase,
    private val deleteDailyUseCase: DeleteDailyUseCase,
    private val getDailyPreSignedURLUseCase: GetDailyPreSignedURLUseCase
) {
    @GetMapping("/daily/pre-signed")
    fun preSignedUrl(
        @Valid
        @RequestBody
        req: CreateDailyRequest
    ): PreSignedURLResponse = getDailyPreSignedURLUseCase.getUploadUrl(req.title!!)

    @PostMapping("/daily")
    @ResponseStatus(HttpStatus.CREATED)
    fun dailyUpload(
        @Valid
        @RequestBody
        req: CreateDailyRequest
    ) {
        createDailyUseCase.create(req.title!!)
    }

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

    @DeleteMapping("/daily/{date}")
    fun dailyDelete(
        @Valid
        @NotNull(message = "null일 수 없습니다.")
        @PathVariable("date", required = false)
        date: LocalDate?
    ) {
        deleteDailyUseCase.delete(date!!)
    }
}
