package com.info.maeumgagym.domain.quote

import com.info.maeumgagym.domain.quote.exception.MismatchQuoteAndQuoterException
import com.info.maeumgagym.quote.port.`in`.ReadRandomQuoteUseCase
import com.info.maeumgagym.quote.vo.QuoteValueObject
import com.info.maeumgagym.quote.vo.Quotes
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class QuoteTests @Autowired constructor(
    private val readRandomQuoteUseCase: ReadRandomQuoteUseCase
) {

    @Test
    fun getRandomQuoteAndContainedInQUOTESTest() {
        Assertions.assertDoesNotThrow {
            for (i in 1..10) {
                val quoteResponse = readRandomQuoteUseCase.readRandomQuote()
                if (Quotes.QUOTES.contains(
                        QuoteValueObject(quoteResponse.quote, quoteResponse.quoter)
                    )
                ) throw MismatchQuoteAndQuoterException
            }
        }
    }
}
