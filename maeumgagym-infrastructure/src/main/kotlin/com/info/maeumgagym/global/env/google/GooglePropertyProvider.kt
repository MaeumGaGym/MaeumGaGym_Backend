package com.info.maeumgagym.global.env.google

import com.info.maeumgagym.auth.google.dto.response.GooglePropertyResponse
import com.info.maeumgagym.auth.port.out.GooglePropertyPort
import com.info.maeumgagym.global.env.BaseProperty
import org.springframework.stereotype.Component

@Component
class GooglePropertyProvider(
    private val googleProperty: GoogleProperty,
    private val baseProperty: BaseProperty
) : GooglePropertyPort {

    override fun getProperty(): GooglePropertyResponse =
        GooglePropertyResponse(
            googleProperty.clientId,
            baseProperty.url + googleProperty.redirectUrl
        )
}
