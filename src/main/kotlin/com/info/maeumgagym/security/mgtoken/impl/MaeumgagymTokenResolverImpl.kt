package com.info.maeumgagym.security.mgtoken.impl

import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.security.mgtoken.MaeumgagymTokenDecoder
import com.info.maeumgagym.security.mgtoken.MaeumgagymTokenResolver
import com.info.maeumgagym.security.mgtoken.MaeumgagymTokenValidator
import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymTokenType
import org.springframework.stereotype.Component

/**
 * Docs는 상위 타입에 존재
 *
 * | Daybreak312
 * [resolveInternal]이 주 함수이며, 외부에서 [MaeumgagymTokenType]에 대한 직접적인 접근이 보기 좋지 않아 함수로 한 번 더 추상화했습니다.
 * | Daybreak312
 *
 * @author Daybreak312
 * @since 20-03-2024
 */
@Component
class MaeumgagymTokenResolverImpl(
    private val maeumgagymTokenDecoder: MaeumgagymTokenDecoder,
    private val maeumgagymTokenValidator: MaeumgagymTokenValidator
) : MaeumgagymTokenResolver {

    override fun resolveAccessToken(token: String): String =
        resolveInternal(token, MaeumgagymTokenType.ACCESS_TOKEN)

    override fun resolveRefreshToken(token: String): String =
        resolveInternal(token, MaeumgagymTokenType.REFRESH_TOKEN)

    private fun resolveInternal(token: String, requiredType: MaeumgagymTokenType): String {
        val decoded = maeumgagymTokenDecoder.decode(token)

        if (decoded.type != requiredType) {
            throw SecurityException.WRONG_TYPE_TOKEN
        }

        maeumgagymTokenValidator.validate(decoded)

        return decoded.username
    }
}
