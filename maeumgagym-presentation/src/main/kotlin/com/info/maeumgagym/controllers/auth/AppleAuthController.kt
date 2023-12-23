package com.info.maeumgagym.controllers.auth

import com.info.common.WebAdapter
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.AppleLoginUseCase
import com.info.maeumgagym.auth.port.`in`.AppleRecoveryUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/apple")
@WebAdapter
class AppleAuthController(
    private val appleLoginUseCase: AppleLoginUseCase,
    private val appleRecoveryUseCase: AppleRecoveryUseCase
) {

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    fun login(@RequestParam("access_token", required = true) token: String): TokenResponse = appleLoginUseCase.execute(
        token
    )

    @PatchMapping("/recovery")
    fun recovery(@RequestParam("access_token", required = true) token: String) { appleRecoveryUseCase.execute() }
}
