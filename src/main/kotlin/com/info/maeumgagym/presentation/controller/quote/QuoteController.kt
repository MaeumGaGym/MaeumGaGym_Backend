package com.info.maeumgagym.presentation.controller.quote

import com.info.maeumgagym.common.annotation.responsibility.WebAdapter
import com.info.maeumgagym.core.quote.dto.response.QuoteResponse
import com.info.maeumgagym.core.quote.port.`in`.ReadRandomQuoteUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Quote API")
@RequestMapping("/quotes")
@WebAdapter
private class QuoteController(
    private val readRandomQuoteUseCase: ReadRandomQuoteUseCase
) {

    @Operation(summary = "동기부여 메세지 랜덤 조회 API")
    @GetMapping
    fun readRandomQuote(): QuoteResponse = readRandomQuoteUseCase.readRandomQuote()
}
