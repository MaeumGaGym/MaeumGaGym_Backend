package com.info.maeumgagym.auth

import com.info.common.WebAdapter
import com.info.maeumgagym.auth.dto.response.GoogleLinkResponse
import com.info.maeumgagym.auth.port.out.GetGoogleLoginPagePort
import com.info.maeumgagym.global.env.BaseProperty
import com.info.maeumgagym.global.env.google.GoogleProperty

@WebAdapter
class GetGoogleLoginPageAdapter(
    private val baseProperty: BaseProperty,
    private val googleProperty: GoogleProperty
) : GetGoogleLoginPagePort {

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
                googleProperty.clientId,
                baseProperty.url + googleProperty.redirectUrl
            )
        )
}
