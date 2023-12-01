package com.info.maeumgagym.auth.controller

import com.info.maeumgagym.auth.port.`in`.WithdrawalUserUseCase
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController(
    private val withdrawalUserUseCase: WithdrawalUserUseCase
) {
    @DeleteMapping
    fun withdrawal() = withdrawalUserUseCase.withdrawal()
}
