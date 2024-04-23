package com.info.maeumgagym.core.quote.service

import com.info.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.quote.dto.response.QuoteResponse
import com.info.maeumgagym.core.quote.port.`in`.ReadRandomQuoteUseCase
import com.info.maeumgagym.core.quote.vo.Quotes

@ReadOnlyUseCase
internal class ReadRandomQuoteService : ReadRandomQuoteUseCase {

    override fun readRandomQuote(): QuoteResponse =
        Quotes.QUOTES.random().run { QuoteResponse(quote, quoter) }
}
