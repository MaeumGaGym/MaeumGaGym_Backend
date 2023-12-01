package com.info.maeumgagym.auth.port.out

import com.info.maeumgagym.auth.dto.response.GoogleInfoResponse

interface GetGoogleInfoPort {

    fun getGoogleInfo(accessToken: String): GoogleInfoResponse
}
