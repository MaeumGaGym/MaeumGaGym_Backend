package com.info.maeumgagym.controller.auth

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.auth.dto.response.TokenWebResponse
import com.info.maeumgagym.auth.port.`in`.AppleLoginUseCase
import com.info.maeumgagym.auth.port.`in`.AppleRecoveryUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Apple OAuth API")
@RequestMapping("/apple")
@WebAdapter
class AppleAuthController(
    private val appleLoginUseCase: AppleLoginUseCase,
    private val appleRecoveryUseCase: AppleRecoveryUseCase
) {

    @Operation(summary = "애플 OAuth 로그인 API")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    fun login(@RequestParam("access_token", required = true) token: String): TokenWebResponse =
        TokenWebResponse.toWebResponse(appleLoginUseCase.execute(token))

    @Operation(summary = "애플 회원복구 API")
    @PatchMapping("/recovery")
    fun recovery(@RequestParam("access_token", required = true) token: String) {
        appleRecoveryUseCase.execute()
    }
}
