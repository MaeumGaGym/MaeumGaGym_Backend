package com.info.maeumgagym.domain.auth

import com.info.maeumgagym.auth.port.`in`.GetGoogleLoginPageUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/oauth/google")
@RestController
class GoogleController(
    private val getGoogleLoginPageUseCase: GetGoogleLoginPageUseCase
) {

    @GetMapping("/login")
    fun getGoogleOAuthLoginPage() =
        getGoogleLoginPageUseCase.getGoogleLoginPage()
}
