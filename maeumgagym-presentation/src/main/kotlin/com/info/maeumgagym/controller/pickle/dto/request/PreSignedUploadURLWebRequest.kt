package com.info.maeumgagym.controller.pickle.dto.request

import javax.validation.constraints.NotBlank

data class PreSignedUploadURLWebRequest(

    @field:NotBlank(message = "null일 수 없습니다")
    val fileType: String?
)