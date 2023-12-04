package com.info.maeumgagym.auth.controller

import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.AppleLoginUseCase
import com.info.maeumgagym.auth.port.`in`.AppleRecoveryUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/apple")
class AppleAuthController(
    private val appleLoginUseCase: AppleLoginUseCase,
    private val appleRecoveryUseCase: AppleRecoveryUseCase
) {

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    fun login(@RequestParam("token", required = true) token: String): TokenResponse = appleLoginUseCase.execute(token)

    @PatchMapping("/recovery")
    fun recovery(@RequestParam("token", required = true) token: String) { appleRecoveryUseCase.execute() }
}
