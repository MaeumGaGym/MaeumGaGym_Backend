package com.info.maeumgagym.controller.auth.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class KakaoSignupWebRequest(

    @field:NotBlank(message = "nickname은 null, 공백, 띄어쓰기를 허용하지 않습니다.")
    @field:Size(min = 2, max = 10, message = "nickname은 최소 2자 최대 10자까지 가능합니다.")
    val nickname: String?
)
