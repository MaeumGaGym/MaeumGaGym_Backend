package com.info.maeumgagym.quote.service

import com.info.common.UseCase
import com.info.maeumgagym.quote.dto.response.QuoteResponse
import com.info.maeumgagym.quote.port.`in`.ReadRandomQuoteUseCase
import com.info.maeumgagym.quote.vo.Quotes

@UseCase
class ReadRandomQuoteService : ReadRandomQuoteUseCase {

    override fun readRandomQuote(): QuoteResponse =
        Quotes.QUOTES.random().run { QuoteResponse(quote, quoter) }
}
