package com.info.maeumgagym.domain.auth

import com.info.maeumgagym.auth.port.`in`.GetGoogleLoginPageUseCase
import com.info.maeumgagym.auth.port.`in`.GoogleLoginUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/oauth/google")
@RestController
class GoogleController(
    private val getGoogleLoginPageService: GetGoogleLoginPageUseCase,
    private val googleLoginService: GoogleLoginUseCase
) {

    @GetMapping("/login")
    fun getGoogleOAuthLoginPage() =
        getGoogleLoginPageService.getGoogleLoginPage()

    @GetMapping("/login/redirect")
    fun login(
        @RequestParam code: String
    ) {
        googleLoginService.googleLogin(code)
    }
}
