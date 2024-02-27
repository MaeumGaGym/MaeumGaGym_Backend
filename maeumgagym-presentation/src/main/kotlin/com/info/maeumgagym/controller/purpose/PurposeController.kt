package com.info.maeumgagym.controller.purpose

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.purpose.dto.CreatePurposeWebRequest
import com.info.maeumgagym.controller.purpose.dto.UpdatePurposeWebRequest
import com.info.maeumgagym.purpose.port.`in`.CreatePurposeUseCase
import com.info.maeumgagym.purpose.port.`in`.DeletePurposeUseCase
import com.info.maeumgagym.purpose.port.`in`.UpdatePurposeUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid
import javax.validation.constraints.Positive

@Tag(name = "Purpose APIs")
@Validated
@RequestMapping("/purposes")
@WebAdapter
class PurposeController(
    private val createPurposeUseCase: CreatePurposeUseCase,
    private val updatePurposeUseCase: UpdatePurposeUseCase,
    private val deletePurposeUseCase: DeletePurposeUseCase
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

    @Operation(summary = "목표 수정 API")
    @PutMapping("/{id}")
    fun purposeUpdate(
        @Positive(message = "1 이상이여야 합니다.")
        @PathVariable
        id: Long,
        @Valid
        @RequestBody
        req: UpdatePurposeWebRequest
    ) {
        updatePurposeUseCase.updatePurposeFromId(id, req.toRequest())
    }

    @Operation(summary = "목표 삭제 API")
    @DeleteMapping("/{id}")
    fun purposeDelete(
        @Positive(message = "1 이상이여야 합니다.")
        @PathVariable
        id: Long
    ) {
        deletePurposeUseCase.deletePurposeFromId(id)
    }
}
