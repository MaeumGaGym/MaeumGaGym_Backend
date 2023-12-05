package com.info.maeumgagym.auth.controller

import com.info.maeumgagym.auth.dto.request.SignupRequest
import com.info.maeumgagym.auth.port.`in`.GoogleLoginUseCase
import com.info.maeumgagym.auth.port.`in`.GoogleSignupUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.NotBlank

@Validated
@RequestMapping("/google")
@RestController
class GoogleOAuthController(
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val googleSignupUseCase: GoogleSignupUseCase
) {

    @PostMapping("/login")
    fun login(@RequestParam("access_token", required = true) @NotBlank accessToken: String?) {
        googleLoginUseCase.login(accessToken!!)
    }

    @PostMapping("/signup")
    fun signup(
        @RequestParam("access_token", required = true) @NotBlank accessToken: String?,
        @RequestBody signupRequest: SignupRequest
    ) {
        googleSignupUseCase.signup(accessToken!!, signupRequest)
    }
}
