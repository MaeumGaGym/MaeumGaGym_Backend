package com.info.maeumgagym.security.mgtoken

import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymTokenPair

/**
 * username을 토큰으로 포장해 암호화
 *
 * 반환되는 [MaeumgagymTokenPair]의 각 토큰은 동일한 [tokenId][com.info.maeumgagym.security.mgtoken.vo.MaeumgagymToken.tokenId]를 가지며, 미리 설정된 [prefix][com.info.maeumgagym.security.mgtoken.env.MaeumgagymTokenProperties.prefix]가 부착됨
 *
 * [MaeumgagymTokenProperties][com.info.maeumgagym.security.mgtoken.env.MaeumgagymTokenProperties]의 만료 기간 관련 설정들은 초 단위로 반영됨
 *
 * @see MaeumgagymTokenDecoder
 * @see MaeumgagymTokenValidator
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
interface MaeumgagymTokenEncoder {

    fun encode(username: String): MaeumgagymTokenPair
}
