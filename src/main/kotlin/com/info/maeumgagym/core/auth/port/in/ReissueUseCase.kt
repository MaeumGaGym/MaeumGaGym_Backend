package com.info.maeumgagym.core.auth.port.`in`

interface ReissueUseCase {

    fun reissue(token: String): Pair<String, String>
}
