package com.info.maeumgagym.auth.port.out

interface ReissuePort {

    fun reissue(refreshToken: String): Pair<String, String>
}
