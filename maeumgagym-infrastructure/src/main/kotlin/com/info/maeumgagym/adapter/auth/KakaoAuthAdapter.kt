package com.info.maeumgagym.adapter.auth

import com.info.maeumgagym.auth.dto.response.KakaoInfoResponse
import com.info.maeumgagym.auth.port.out.GetKakaoInfoPort
import com.info.maeumgagym.common.exception.FeignException
import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.feign.oauth.kakao.KakaoInfoClient
import org.springframework.stereotype.Component

@Component
internal class KakaoAuthAdapter(
    private val kakaoInfoClient: KakaoInfoClient
) : GetKakaoInfoPort {

    private companion object {
        const val PREFIX = "Bearer "
    }

    override fun getInfo(accessToken: String): KakaoInfoResponse = try {
        kakaoInfoClient.kakaoInfo(
            PREFIX + accessToken
        ).toResponse()
    } catch (e: FeignException) {
        throw if (e == FeignException.FEIGN_UNAUTHORIZED) SecurityException.INVALID_TOKEN
        else MaeumGaGymException.INTERNAL_SERVER_ERROR
    }
}
