package com.info.maeumgagym.infrastructure.auth.oauth

import com.info.maeumgagym.core.auth.dto.response.KakaoProfileResponse
import com.info.maeumgagym.core.auth.port.out.GetKakaoProfilePort
import com.info.maeumgagym.core.auth.port.out.GetKakaoTokenPort
import com.info.maeumgagym.core.auth.port.out.RevokeKakaoTokenPort
import com.info.maeumgagym.core.common.exception.FeignException
import com.info.maeumgagym.core.common.exception.MaeumGaGymException
import com.info.maeumgagym.core.common.exception.SecurityException
import com.info.maeumgagym.infrastructure.env.feign.KakaoProperties
import com.info.maeumgagym.infrastructure.external.feign.oauth.kakao.KakaoApiClient
import com.info.maeumgagym.infrastructure.external.feign.oauth.kakao.KakaoAuthClient
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
