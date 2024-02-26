package com.info.maeumgagym.adapter.auth

import com.info.maeumgagym.auth.dto.response.GoogleInfoResponse
import com.info.maeumgagym.auth.port.out.GetGoogleInfoPort
import com.info.maeumgagym.auth.port.out.RevokeGoogleTokenPort
import com.info.maeumgagym.common.exception.FeignException
import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.feign.oauth.google.GoogleAuthClient
import com.info.maeumgagym.feign.oauth.google.GoogleInfoClient
import org.springframework.stereotype.Component

@Component
internal class GoogleAuthAdapter(
    private val googleInfoClient: GoogleInfoClient,
    private val googleAuthClient: GoogleAuthClient
) : GetGoogleInfoPort, RevokeGoogleTokenPort {

    private companion object {
        const val ALT = "json"
    }

    override fun getGoogleInfo(accessToken: String): GoogleInfoResponse = try {
        googleInfoClient.googleInfo(
            ALT,
            accessToken
        ).toResponse()
    } catch (e: FeignException) {
        throw if (e == FeignException.FEIGN_UNAUTHORIZED) {
            SecurityException.INVALID_TOKEN
        } else {
            MaeumGaGymException.INTERNAL_SERVER_ERROR
        }
    }

    override fun revoke(token: String) {
        try {
            googleAuthClient.revokeToken(token)
        } catch (e: FeignException) {
            throw MaeumGaGymException.INTERNAL_SERVER_ERROR
        }
    }
}
