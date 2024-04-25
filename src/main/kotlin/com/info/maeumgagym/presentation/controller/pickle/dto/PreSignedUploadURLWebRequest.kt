package com.info.maeumgagym.presentation.controller.pickle.dto

import javax.validation.constraints.NotBlank

data class PreSignedUploadURLWebRequest(

    @field:NotBlank(message = "null일 수 없습니다")
    val fileType: String?
)
