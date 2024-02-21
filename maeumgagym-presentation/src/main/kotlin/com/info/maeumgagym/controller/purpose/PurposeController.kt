package com.info.maeumgagym.controller.purpose

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.purpose.dto.CreatePurposeWebRequest
import com.info.maeumgagym.purpose.port.`in`.CreatePurposeUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

@Tag(name = "Purpose APIs")
@Validated
@RequestMapping("/purposes")
@WebAdapter
class PurposeController(
    private val createPurposeUseCase: CreatePurposeUseCase
) {

    @Operation(summary = "목표 생성 API")
    @PostMapping
    fun purposeCreate(
        @Valid
        @RequestBody
        req: CreatePurposeWebRequest
    ) {
        createPurposeUseCase.createPurpose(req.toRequest())
    }
}
