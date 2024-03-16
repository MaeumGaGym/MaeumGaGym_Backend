package com.info.maeumgagym.controller.wakatime

import com.info.common.WebAdapter
import com.info.maeumgagym.wakatime.port.`in`.EndWakatimeUseCase
import com.info.maeumgagym.wakatime.port.`in`.StartWakatimeUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Tag(name = "Wakatime API")
@Validated
@WebAdapter
@RequestMapping("/waka")
class WakatimeController(
    private val startWakatimeUseCase: StartWakatimeUseCase,
    private val endWakatimeUseCase: EndWakatimeUseCase
) {

    @Operation(summary = "와카타임 시작 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/start")
    fun startWakatime() {
        startWakatimeUseCase.startWakatime()
    }

    @Operation(summary = "와카 타임 종료 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/end")
    fun endWakatime() {
        endWakatimeUseCase.endWakatime()
    }
}
