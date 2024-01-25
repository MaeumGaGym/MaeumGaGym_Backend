package com.info.maeumgagym.auth.port.`in`

interface ReissueUseCase {

    fun reissue(token: String): Pair<String, String>
}
