package com.info.maeumgagym.pickle.dto.request

import javax.validation.constraints.NotBlank

data class PreSignedUploadURLRequest(

    @field:NotBlank(message = "null일 수 없습니다")
    val fileType: String?
)
