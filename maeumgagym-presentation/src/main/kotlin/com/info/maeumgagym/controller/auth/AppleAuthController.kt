package com.info.maeumgagym.controller.auth

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.auth.dto.response.TokenWebResponse
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
    fun login(@RequestParam("access_token", required = true) token: String): TokenWebResponse =
        TokenWebResponse.toWebResponse(appleLoginUseCase.execute(token))

    @PatchMapping("/recovery")
    fun recovery(@RequestParam("access_token", required = true) token: String) {
        appleRecoveryUseCase.execute()
    }
}
