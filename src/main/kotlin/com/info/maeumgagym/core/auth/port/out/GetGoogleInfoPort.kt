package com.info.maeumgagym.core.auth.port.out

import com.info.maeumgagym.core.auth.dto.response.GoogleInfoResponse

interface GetGoogleInfoPort {

    fun getGoogleInfo(accessToken: String): GoogleInfoResponse
}
