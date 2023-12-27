package com.info.maeumgagym.controller.quote

import com.info.common.WebAdapter
import com.info.maeumgagym.controller.quote.dto.response.QuoteWebResponse
import com.info.maeumgagym.quote.port.`in`.ReadRandomQuoteUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Quote API")
@RequestMapping("/quotes")
@WebAdapter
class QuoteController(
    private val readRandomQuoteUseCase: ReadRandomQuoteUseCase
) {

    @Operation(summary = "동기부여 메세지 랜덤 조회 API")
    @GetMapping
    fun readRandomQuote(): QuoteWebResponse =
        QuoteWebResponse.toWebResponse(
            readRandomQuoteUseCase.readRandomQuote()
        )

}
