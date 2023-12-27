package com.info.maeumgagym.quote.port.`in`

import com.info.maeumgagym.quote.dto.response.QuoteResponse

interface ReadRandomQuoteUseCase {

    fun readRandomQuote(): QuoteResponse
}
