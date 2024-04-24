package com.info.maeumgagym.core.quote.service

import com.info.maeumgagym.common.annotation.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.quote.dto.response.QuoteResponse
import com.info.maeumgagym.quote.port.`in`.ReadRandomQuoteUseCase
import com.info.maeumgagym.quote.vo.Quotes

@ReadOnlyUseCase
internal class ReadRandomQuoteService : ReadRandomQuoteUseCase {

    override fun readRandomQuote(): QuoteResponse =
        Quotes.QUOTES.random().run { QuoteResponse(quote, quoter) }
}
