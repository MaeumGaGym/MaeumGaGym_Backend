package com.info.maeumgagym.auth.port.`in`

import com.info.maeumgagym.auth.dto.request.SignupRequest

interface GoogleSignupUseCase {

    fun signup(accessToken: String, signupRequest: SignupRequest)
}
