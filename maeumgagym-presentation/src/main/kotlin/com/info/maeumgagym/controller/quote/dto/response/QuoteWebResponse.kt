package com.info.maeumgagym.controller.quote.dto.response

import com.info.maeumgagym.quote.dto.response.QuoteResponse

data class QuoteWebResponse(

    val quote: String,

    val quoter: String
) {

    companion object {
        fun toWebResponse(response: QuoteResponse): QuoteWebResponse =
            QuoteWebResponse(
                response.quote,
                response.quoter
            )
    }
}
