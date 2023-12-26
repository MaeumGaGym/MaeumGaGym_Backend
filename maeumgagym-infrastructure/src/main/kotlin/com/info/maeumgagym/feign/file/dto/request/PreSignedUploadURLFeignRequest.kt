package com.info.maeumgagym.feign.file.dto.request

import javax.validation.constraints.NotBlank

data class PreSignedUploadURLFeignRequest(

    @field:NotBlank(message = "null일 수 없습니다")
    val fileType: String?
)
