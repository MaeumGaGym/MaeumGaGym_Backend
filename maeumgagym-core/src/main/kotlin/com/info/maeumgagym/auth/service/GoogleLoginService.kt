package com.info.maeumgagym.auth.service

import com.info.maeumgagym.auth.port.`in`.GoogleLoginUseCase
import com.info.maeumgagym.auth.port.out.CreateUserPort
import com.info.maeumgagym.auth.port.out.GetGoogleAccessTokenPort
import com.info.maeumgagym.auth.port.out.GetGoogleInfoPort
import org.springframework.stereotype.Service

@Service
class GoogleLoginService(
    private val getGoogleAccessTokenPort: GetGoogleAccessTokenPort,
    private val getGoogleInfoPort: GetGoogleInfoPort,
//    private val createUserPort: CreateUserPort
) : GoogleLoginUseCase {

    override fun googleLogin(code: String) {
        val googleTokenResponse = getGoogleAccessTokenPort.getGoogleAccessToken(code)
        val googleInfoResponse = getGoogleInfoPort.getGoogleInfo(googleTokenResponse.accessToken)
        with(googleInfoResponse) {
            TODO("UserRepository 및 관련 기능이 구현되지 않음")
//            createUserPort.createUser(sub, name, picture)
        }
    }
}
