package com.info.maeumgagym.controller.auth

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.auth.dto.response.TokenWebResponse
import com.info.maeumgagym.auth.port.`in`.AppleLoginUseCase
import com.info.maeumgagym.auth.port.`in`.AppleRecoveryUseCase
import com.info.maeumgagym.auth.port.`in`.AppleSignUpUseCase
import com.info.maeumgagym.controller.auth.dto.request.SignupWebRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "Apple OAuth API")
@RequestMapping("/apple")
@WebAdapter
class AppleAuthController(
    private val appleLoginUseCase: AppleLoginUseCase,
    private val appleRecoveryUseCase: AppleRecoveryUseCase,
    private val appleSignUpUseCase: AppleSignUpUseCase
) {

    @Operation(summary = "애플 OAuth 로그인 API")
    @GetMapping("/login")
    fun login(@RequestParam("access_token", required = true) token: String): TokenWebResponse =
        TokenWebResponse.toWebResponse(appleLoginUseCase.login(token))

    @Operation(summary = "애플 OAuth 회원가입 API")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(
        @RequestParam("access_token", required = true)
        token: String,
        @RequestBody
        @Valid
        req: SignupWebRequest
    ) { appleSignUpUseCase.signUp(token, req.nickname!!) }

    @Operation(summary = "애플 OAuth 회원복구 API")
    @PutMapping("/recovery")
    fun recovery(@RequestParam("access_token", required = true) token: String) {
        appleRecoveryUseCase.recovery(token)
    }
}
