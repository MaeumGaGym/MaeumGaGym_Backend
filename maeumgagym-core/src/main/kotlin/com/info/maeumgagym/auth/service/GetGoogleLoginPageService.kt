package com.info.maeumgagym.auth.service

import com.info.maeumgagym.auth.dto.response.GoogleLinkResponse
import com.info.maeumgagym.auth.port.`in`.GetGoogleLoginPageUseCase
import com.info.maeumgagym.auth.port.out.GetGoogleLoginPagePort
import org.springframework.stereotype.Service

@Service
class GetGoogleLoginPageService(
    private val getGoogleLoginPagePort: GetGoogleLoginPagePort
) : GetGoogleLoginPageUseCase {

    override fun getGoogleLoginPage(): GoogleLinkResponse =
        getGoogleLoginPagePort.getGoogleLoginPage()
}
