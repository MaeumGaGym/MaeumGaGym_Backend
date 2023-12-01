package com.info.maeumgagym.auth.controller

import com.info.maeumgagym.auth.port.`in`.GoogleLoginUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.NotNull

@Validated
@RequestMapping("/google")
@RestController
class GoogleAuthController(
    private val googleLoginService: GoogleLoginUseCase
) {

    @PostMapping("/login")
    fun login(@RequestParam(required = true) @NotNull accessToken: String?) {
        googleLoginService.googleLogin(accessToken!!)
    }
}
