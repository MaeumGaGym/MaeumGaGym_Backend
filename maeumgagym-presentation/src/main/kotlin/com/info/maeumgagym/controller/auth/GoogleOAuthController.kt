package com.info.maeumgagym.controller.auth

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.auth.dto.request.SignupWebRequest
import com.info.maeumgagym.controller.auth.dto.response.TokenWebResponse
import com.info.maeumgagym.auth.port.`in`.GoogleLoginUseCase
import com.info.maeumgagym.auth.port.`in`.GoogleSignupUseCase
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Validated
@WebAdapter
@RequestMapping("/google")
class GoogleOAuthController(
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val googleSignupUseCase: GoogleSignupUseCase
) {

    @PostMapping("/login")
    fun login(@RequestParam("access_token", required = true) @NotBlank accessToken: String?): TokenWebResponse =
        TokenWebResponse.toWebResponse(googleLoginUseCase.login(accessToken!!))

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(
        @RequestParam("access_token", required = true)
        @Valid
        @NotBlank
        accessToken: String?,
        @RequestBody
        @Valid
        req: SignupWebRequest
    ) {
        googleSignupUseCase.signup(accessToken!!, req.nickname!!)
    }
}
