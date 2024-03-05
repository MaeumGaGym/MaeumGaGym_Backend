package com.info.maeumgagym.controller.daily

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.daily.dto.CreateDailyRequest
import com.info.maeumgagym.daily.dto.response.DailyListResponse
import com.info.maeumgagym.daily.dto.response.PreSignedURLResponse
import com.info.maeumgagym.daily.port.`in`.CreateDailyUseCase
import com.info.maeumgagym.daily.port.`in`.GetDailyPreSignedURLUseCase
import com.info.maeumgagym.daily.port.`in`.ReadDailyUseCase
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import javax.validation.Valid
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

@Tag(name = "Daily Exercise Complete API")
@Validated
@WebAdapter
class DailyController(
    private val createDailyUseCase: CreateDailyUseCase,
    private val readDailyUseCase: ReadDailyUseCase,
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
        @PositiveOrZero
        @RequestParam("page", required = false, defaultValue = "0")
        page: Int,
        @Valid
        @Positive
        @RequestParam("size", required = false, defaultValue = "15")
        size: Int
    ): DailyListResponse = readDailyUseCase.readDailies(page, size)
}
