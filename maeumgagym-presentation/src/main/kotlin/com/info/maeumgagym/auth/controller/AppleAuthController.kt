package com.info.maeumgagym.auth.controller

import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.AppleLoginUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/apple")
class AppleAuthController(private val appleLoginUseCase: AppleLoginUseCase) {

    @PostMapping("/login")
    fun login(@RequestParam("token", required = true) token: String): TokenResponse = appleLoginUseCase.execute(token)
}
