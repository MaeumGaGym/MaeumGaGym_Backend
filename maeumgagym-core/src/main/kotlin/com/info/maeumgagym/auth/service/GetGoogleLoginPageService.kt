package com.info.maeumgagym.auth.service

import com.info.maeumgagym.auth.dto.response.GoogleLinkResponse
import com.info.maeumgagym.auth.port.`in`.GetGoogleLoginPageUseCase
import com.info.maeumgagym.auth.port.out.GooglePropertyPort
import org.springframework.stereotype.Service

@Service
class GetGoogleLoginPageService(
    private val googlePropertyPort: GooglePropertyPort
) : GetGoogleLoginPageUseCase {

    private companion object {
        const val GOOGLE_URL =
            "https://accounts.google.com/o/oauth2/v2/auth?" +
                "client_id=%s&" +
                "response_type=code&" +
                "redirect_uri=%s&" +
                "scope=https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile"
    }

    override fun getGoogleLoginPage(): GoogleLinkResponse =
        GoogleLinkResponse(
            GOOGLE_URL.format(
                googlePropertyPort.getProperty().clientId,
                googlePropertyPort.getProperty().redirectUrl
            )
        )
}
