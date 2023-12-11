package com.info.maeumgagym.auth.adapter

import com.info.maeumgagym.auth.dto.response.GoogleInfoResponse
import com.info.maeumgagym.auth.port.out.GetGoogleInfoPort
import com.info.maeumgagym.feign.oauth.google.GoogleInfoClient
import org.springframework.stereotype.Component

@Component
class GoogleAuthAdapter(
    private val googleInfoClient: GoogleInfoClient
) : GetGoogleInfoPort {

    override fun getGoogleInfo(accessToken: String): GoogleInfoResponse =
        googleInfoClient.googleInfo(
            "json",
            accessToken
        )
}
