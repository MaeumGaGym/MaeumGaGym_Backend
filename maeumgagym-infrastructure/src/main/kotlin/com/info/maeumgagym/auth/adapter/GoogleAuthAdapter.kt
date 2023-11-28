package com.info.maeumgagym.auth.adapter

import com.info.common.WebAdapter
import com.info.maeumgagym.auth.google.dto.response.GoogleInfoResponse
import com.info.maeumgagym.auth.google.dto.response.GoogleTokenResponse
import com.info.maeumgagym.auth.port.out.GetGoogleAccessTokenPort
import com.info.maeumgagym.auth.port.out.GetGoogleInfoPort
import com.info.maeumgagym.feign.oauth.google.GoogleAuthClient
import com.info.maeumgagym.feign.oauth.google.GoogleInfoClient
import com.info.maeumgagym.global.env.BaseProperty
import com.info.maeumgagym.global.env.google.GoogleProperty

@WebAdapter
class GoogleAuthAdapter(
    private val baseProperty: BaseProperty,
    private val googleProperty: GoogleProperty,
    private val googleAuthClient: GoogleAuthClient,
    private val googleInfoClient: GoogleInfoClient
) : GetGoogleAccessTokenPort, GetGoogleInfoPort {

    override fun getGoogleAccessToken(code: String): GoogleTokenResponse =
        googleAuthClient.googleAuth(
            code = code,
            clientId = googleProperty.clientId,
            clientSecret = googleProperty.clientSecret,
            redirectUri = baseProperty.url + googleProperty.redirectUrl,
            grantType = googleProperty.grantType
        )

    override fun getGoogleInfo(accessToken: String): GoogleInfoResponse =
        googleInfoClient.googleInfo(
            "json",
            accessToken
        )
}
