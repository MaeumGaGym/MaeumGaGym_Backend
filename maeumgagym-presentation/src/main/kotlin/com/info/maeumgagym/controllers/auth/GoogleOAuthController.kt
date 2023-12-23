package com.info.maeumgagym.controllers.auth

import com.info.common.WebAdapter
import com.info.maeumgagym.auth.dto.request.SignupRequest
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.GoogleLoginUseCase
import com.info.maeumgagym.auth.port.`in`.GoogleSignupUseCase
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.NotBlank

@Validated
@RequestMapping("/google")
@WebAdapter
class GoogleOAuthController(
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val googleSignupUseCase: GoogleSignupUseCase
) {

    @PostMapping("/login")
    fun login(@RequestParam("access_token", required = true) @NotBlank accessToken: String?): TokenResponse =
        googleLoginUseCase.login(accessToken!!)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signup(
        @RequestParam("access_token", required = true) @NotBlank accessToken: String?,
        @RequestBody signupRequest: SignupRequest
    ) {
        googleSignupUseCase.signup(accessToken!!, signupRequest)
    }
}
