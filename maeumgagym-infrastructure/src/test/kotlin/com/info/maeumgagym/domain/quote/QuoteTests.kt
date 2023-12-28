package com.info.maeumgagym.domain.quote

import com.info.maeumgagym.domain.quote.exception.MismatchQuoteAndQuoterException
import com.info.maeumgagym.quote.service.ReadRandomQuoteService
import com.info.maeumgagym.quote.vo.QuoteValueObject
import com.info.maeumgagym.quote.vo.Quotes
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@AutoConfigureMockMvc
@SpringBootTest
class QuoteTests @Autowired constructor(
    private val readRandomQuoteService: ReadRandomQuoteService
) {

    @Test
    fun getRandomQuoteAndContainedInQUOTESTest() {
        Assertions.assertDoesNotThrow {
            for (i in 1..10) {
                val quoteResponse = readRandomQuoteService.readRandomQuote()
                if (!Quotes.QUOTES.contains(
                        QuoteValueObject(quoteResponse.quote, quoteResponse.quoter)
                    )
                ) throw MismatchQuoteAndQuoterException
            }
        }
    }
}
