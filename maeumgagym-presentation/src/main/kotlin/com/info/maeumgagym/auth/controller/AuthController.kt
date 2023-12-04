package com.info.maeumgagym.auth.controller

import com.info.maeumgagym.auth.dto.request.ReissueRequest
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.`in`.ReissueUseCase
import com.info.maeumgagym.auth.port.`in`.WithdrawalUserUseCase
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController(
    private val withdrawalUserUseCase: WithdrawalUserUseCase,
    private val reissueUseCase: ReissueUseCase
) {
    @DeleteMapping
    fun withdrawal() = withdrawalUserUseCase.withdrawal()

    @PostMapping("reissue")
    fun reissue(@RequestBody request: ReissueRequest): TokenResponse = reissueUseCase.reissue(request)
}
