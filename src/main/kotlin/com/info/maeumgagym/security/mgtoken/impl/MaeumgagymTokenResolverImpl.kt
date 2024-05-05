package com.info.maeumgagym.security.mgtoken.impl

import com.info.maeumgagym.security.mgtoken.MaeumgagymTokenDecoder
import com.info.maeumgagym.security.mgtoken.MaeumgagymTokenResolver
import com.info.maeumgagym.security.mgtoken.MaeumgagymTokenValidator
import org.springframework.stereotype.Component

/**
 * Docs는 상위 타입에 존재
 *
 * @author Daybreak312
 * @since 20-03-2024
 */
@Component
class MaeumgagymTokenResolverImpl(
    private val maeumgagymTokenDecoder: MaeumgagymTokenDecoder,
    private val maeumgagymTokenValidator: MaeumgagymTokenValidator
) : MaeumgagymTokenResolver {

    override fun invoke(token: String): String? {

        val decoded = maeumgagymTokenDecoder.decode(token)

        maeumgagymTokenValidator.validate(decoded)

        return decoded.username
    }
}