package com.info.maeumgagym.auth.controller

import com.info.maeumgagym.auth.port.`in`.GetKakaoAccessTokenUseCase
import org.springframework.web.bind.annotation.RestController

@RestController
class KakaoAuthController(
    private val getKakaoAccessTokenUseCase: GetKakaoAccessTokenUseCase
) {

}
