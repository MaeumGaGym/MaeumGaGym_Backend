package com.info.maeumgagym.controller.wakatime

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.wakatime.dto.AddWakaTimeWebRequest
import com.info.maeumgagym.wakatime.service.port.`in`.AddWakatimeUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

@Tag(name = "Wakatime API")
@Validated
@WebAdapter
@RequestMapping("/waka")
class WakatimeController(
    private val addWakatimeUseCase: AddWakatimeUseCase
) {

    @Operation(summary = "와카타임 저장 API")
    @PostMapping
    fun addWakatime(
        @RequestBody @Valid
        req: AddWakaTimeWebRequest
    ) {
        addWakatimeUseCase.addWakatime(req.seconds!!)
    }
}
