package com.info.maeumgagym.feign.error

import com.info.maeumgagym.common.exception.FeignException
import com.info.maeumgagym.common.exception.MaeumGaGymException
import feign.Response
import feign.codec.ErrorDecoder
import mu.KotlinLogging

class FeignClientErrorDecoder : ErrorDecoder {

    private companion object {
        val logger = KotlinLogging.logger {}
    }

    override fun decode(methodKey: String, response: Response): Exception {
        logger.error { "${response.status()} ${response.reason()} : $methodKey | ${response.body()}\n$response" }

        if (response.status() >= 400) {
            when (response.status()) {
                400 -> throw FeignException.FEIGN_BAD_REQUEST
                401 -> throw FeignException.FEIGN_UNAUTHORIZED
                403 -> throw FeignException.FEIGN_FORBIDDEN
                419 -> throw FeignException(419, "Page Expired")
                else -> throw FeignException.FEIGN_UNKNOWN_CLIENT_ERROR
            }
        }

        return feign.FeignException.errorStatus(methodKey, response)
    }
}
