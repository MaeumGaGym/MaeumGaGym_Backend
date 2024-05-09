package com.info.maeumgagym.presentation.controller.wakatime

import com.info.maeumgagym.common.annotation.responsibility.WebAdapter
import com.info.maeumgagym.common.annotation.security.RequireAuthentication
import com.info.maeumgagym.core.wakatime.dto.response.WakatimeResponse
import com.info.maeumgagym.core.wakatime.port.`in`.EndWakatimeUseCase
import com.info.maeumgagym.core.wakatime.port.`in`.ReadWakaTimeUseCase
import com.info.maeumgagym.core.wakatime.port.`in`.StartWakatimeUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Tag(name = "Wakatime API")
@Validated
@WebAdapter
@RequestMapping("/waka")
private class WakatimeController(
    private val startWakatimeUseCase: StartWakatimeUseCase,
    private val endWakatimeUseCase: EndWakatimeUseCase,
    private val readWakatimeUseCase: ReadWakaTimeUseCase
) {

    @Operation(summary = "와카타임 시작 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequireAuthentication
    @PostMapping("/start")
    fun startWakatime() {
        startWakatimeUseCase.startWakatime()
    }

    @Operation(summary = "와카타임 종료 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequireAuthentication
    @PostMapping("/end")
    fun endWakatime() {
        endWakatimeUseCase.endWakatime()
    }

    @Operation(summary = "와카타임 총시간 보기 API")
    @RequireAuthentication
    @GetMapping("/total")
    fun readTotalWakatime(): WakatimeResponse = readWakatimeUseCase.readTotalSeconds()

    @Operation(summary = "와카타임 오늘 운동시간 보기 API")
    @RequireAuthentication
    @GetMapping("/daily")
    fun readDailyWakatime(): WakatimeResponse = readWakatimeUseCase.readDailySeconds()
}
