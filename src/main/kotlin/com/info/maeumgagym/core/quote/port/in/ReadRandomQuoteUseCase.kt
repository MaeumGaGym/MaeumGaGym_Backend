package com.info.maeumgagym.core.quote.port.`in`

import com.info.maeumgagym.core.quote.dto.response.QuoteResponse

interface ReadRandomQuoteUseCase {

    fun readRandomQuote(): QuoteResponse
}
