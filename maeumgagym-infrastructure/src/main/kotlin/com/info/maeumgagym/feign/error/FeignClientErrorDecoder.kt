package com.info.maeumgagym.feign.error

import com.info.maeumgagym.feign.exception.FeignBadRequestException
import com.info.maeumgagym.feign.exception.FeignDefaultException
import com.info.maeumgagym.feign.exception.FeignForbbidenException
import com.info.maeumgagym.feign.exception.FeignUnAuthorizationException
import com.info.maeumgagym.auth.exception.ExpiredTokenException
import feign.FeignException
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
                400 -> throw FeignBadRequestException
                401 -> throw FeignUnAuthorizationException
                403 -> throw FeignForbbidenException
                419 -> throw ExpiredTokenException
                else -> throw FeignDefaultException
            }
        }

        return FeignException.errorStatus(methodKey, response)
    }
}
