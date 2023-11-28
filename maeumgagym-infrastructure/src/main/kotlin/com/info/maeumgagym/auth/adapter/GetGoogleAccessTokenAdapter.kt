package com.info.maeumgagym.auth.adapter

import com.info.maeumgagym.auth.google.dto.response.GoogleTokenResponse
import com.info.maeumgagym.auth.port.out.GetGoogleAccessTokenPort
import com.info.maeumgagym.feign.oauth.google.GoogleAuthClient
import com.info.maeumgagym.global.env.BaseProperty
import com.info.maeumgagym.global.env.google.GoogleProperty
import org.springframework.stereotype.Component

@Component
class GetGoogleAccessTokenAdapter(
    private val baseProperty: BaseProperty,
    private val googleProperty: GoogleProperty,
    private val googleAuthClient: GoogleAuthClient
) : GetGoogleAccessTokenPort {

    override fun getGoogleAccessToken(code: String): GoogleTokenResponse =
        googleAuthClient.googleAuth(
            code = code,
            clientId = googleProperty.clientId,
            clientSecret = googleProperty.clientSecret,
            redirectUri = baseProperty.url + googleProperty.redirectUrl,
            grantType = googleProperty.grantType
        )
}
