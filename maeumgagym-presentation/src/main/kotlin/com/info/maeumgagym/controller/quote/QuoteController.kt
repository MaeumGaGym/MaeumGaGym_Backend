package com.info.maeumgagym.controller.quote

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.quote.dto.response.QuoteWebResponse
import com.info.maeumgagym.quote.port.`in`.ReadRandomQuoteUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/quotes")
@WebAdapter
class QuoteController(
    private val readRandomQuoteUseCase: ReadRandomQuoteUseCase
) {

    @GetMapping
    fun readRandomQuote(): QuoteWebResponse =
        QuoteWebResponse.toWebResponse(
            readRandomQuoteUseCase.readRandomQuote()
        )

}
