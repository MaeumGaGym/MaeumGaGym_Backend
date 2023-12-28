package com.info.maeumgagym.adapter.auth

import com.info.maeumgagym.auth.dto.response.GoogleInfoResponse
import com.info.maeumgagym.auth.port.out.GetGoogleInfoPort
import com.info.maeumgagym.auth.port.out.RevokeGoogleTokenPort
import com.info.maeumgagym.feign.oauth.google.GoogleAuthClient
import com.info.maeumgagym.feign.oauth.google.GoogleInfoClient
import org.springframework.stereotype.Component

@Component
class GoogleAuthAdapter(
    private val googleInfoClient: GoogleInfoClient,
    private val googleAuthClient: GoogleAuthClient
) : GetGoogleInfoPort, RevokeGoogleTokenPort {

    override fun getGoogleInfo(accessToken: String): GoogleInfoResponse =
        googleInfoClient.googleInfo(
            "json",
            accessToken
        ).toResponse()

    override fun revokeGoogleToken(token: String) {
        googleAuthClient.revokeToken(token)
    }
}
