package com.info.maeumgagym.auth.adapter

import com.info.maeumgagym.auth.dto.response.KakaoTokenResponse
import com.info.maeumgagym.auth.port.out.GetKakaoAccessTokenPort
import com.info.maeumgagym.env.config.kakao.KakaoProperties
import com.info.maeumgagym.feign.oauth.kakao.KakaoAuthClient

class KakaoAuthAdapter(
    private val kakaoAuthClient: KakaoAuthClient,
    private val kakaoProperties: KakaoProperties
): GetKakaoAccessTokenPort {
    override fun getAccessToken(code: String): KakaoTokenResponse {
        return kakaoAuthClient.kakaoAuth(
            grantType = kakaoProperties.grantType,
            clientId = kakaoProperties.clientId,
            redirectUri = kakaoProperties.redirectUri,
            code = code
        )
    }
}
