package com.info.maeumgagym.controller.wakatime

import com.info.common.WebAdapter
import com.info.maeumgagym.wakatime.port.`in`.StartWakatimeUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Wakatime API")
@Validated
@WebAdapter
@RequestMapping("/waka")
class WakatimeController(
    private val startWakatimeUseCase: StartWakatimeUseCase
) {

    @Operation(summary = "와카타임 시작 API")
    @PostMapping
    fun startWakatime() {
        startWakatimeUseCase.startWakatime()
    }
}
