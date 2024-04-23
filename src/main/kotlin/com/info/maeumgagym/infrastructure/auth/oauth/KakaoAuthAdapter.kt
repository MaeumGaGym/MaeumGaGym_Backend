package com.info.maeumgagym.auth.oauth

import com.info.maeumgagym.auth.dto.response.KakaoProfileResponse
import com.info.maeumgagym.auth.port.out.GetKakaoProfilePort
import com.info.maeumgagym.auth.port.out.GetKakaoTokenPort
import com.info.maeumgagym.auth.port.out.RevokeKakaoTokenPort
import com.info.maeumgagym.common.exception.FeignException
import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.env.feign.KakaoProperties
import com.info.maeumgagym.external.feign.oauth.kakao.KakaoApiClient
import com.info.maeumgagym.external.feign.oauth.kakao.KakaoAuthClient
import org.springframework.stereotype.Component

@Component
internal class KakaoAuthAdapter(
    private val kakaoApiClient: KakaoApiClient,
    private val kakaoAuthClient: KakaoAuthClient,
    private val kakaoProperties: KakaoProperties
) : GetKakaoProfilePort, RevokeKakaoTokenPort, GetKakaoTokenPort {

    private companion object {
        const val PREFIX = "Bearer "
    }

    override fun getProfile(accessToken: String): KakaoProfileResponse = try {
        kakaoApiClient.kakaoProfile(
            PREFIX + accessToken
        ).toResponse()
    } catch (e: FeignException) {
        throw if (e.message == FeignException.FEIGN_UNAUTHORIZED.message ||
            e.message == FeignException.FEIGN_FORBIDDEN.message ||
            e.message == FeignException.FEIGN_BAD_REQUEST.message
        ) {
            SecurityException.INVALID_TOKEN
        } else {
            MaeumGaGymException.INTERNAL_SERVER_ERROR
        }
    }

    override fun getToken(code: String): String = kakaoAuthClient.kakaoAuth(
        code = code,
        clientId = kakaoProperties.clientId,
        clientSecret = kakaoProperties.secretKey,
        redirectUri = kakaoProperties.redirectionUri
    ).accessToken

    override fun revoke(accessToken: String) {
        kakaoApiClient.revoke(PREFIX + accessToken)
    }
}
