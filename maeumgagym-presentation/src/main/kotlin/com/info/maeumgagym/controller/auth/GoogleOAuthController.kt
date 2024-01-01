package com.info.maeumgagym.controller.auth

import com.info.common.WebAdapter
import com.info.maeumgagym.auth.port.`in`.GoogleLoginUseCase
import com.info.maeumgagym.auth.port.`in`.GoogleRecoveryUseCase
import com.info.maeumgagym.auth.port.`in`.GoogleSignupUseCase
import com.info.maeumgagym.controller.auth.dto.request.SignupWebRequest
import com.info.maeumgagym.controller.auth.dto.response.TokenWebResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Tag(name = "Google OAuth API")
@Validated
@WebAdapter
@RequestMapping("/google")
class GoogleOAuthController(
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val googleSignupUseCase: GoogleSignupUseCase,
    private val googleRecoveryUseCase: GoogleRecoveryUseCase
) {
    @Operation(summary = "구글 OAuth 회원복구 API")
    @PutMapping("/recovery")
    fun recovery(@RequestParam("access_token") accessToken: String) {
        googleRecoveryUseCase.recovery(accessToken)
    }

    @Operation(summary = "구글 OAuth 로그인 API")
    @GetMapping("/login")
    fun login(@RequestParam("access_token") @Valid @NotBlank accessToken: String?): TokenWebResponse =
        TokenWebResponse.toWebResponse(googleLoginUseCase.login(accessToken!!))

    @Operation(summary = "구글 OAuth 회원가입 API")
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
