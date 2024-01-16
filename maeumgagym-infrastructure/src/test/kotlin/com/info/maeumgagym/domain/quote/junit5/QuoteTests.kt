package com.info.maeumgagym.domain.quote.junit5

import com.info.maeumgagym.domain.quote.exception.MismatchQuoteAndQuoterException
import com.info.maeumgagym.quote.port.`in`.ReadRandomQuoteUseCase
import com.info.maeumgagym.quote.vo.QuoteValueObject
import com.info.maeumgagym.quote.vo.Quotes
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.RepeatedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class QuoteTests @Autowired constructor(
    private val readRandomQuoteService: ReadRandomQuoteUseCase
) {

    @RepeatedTest(value = 10)
    fun getRandomQuoteAndContainedInQUOTESTest() {
        Assertions.assertDoesNotThrow {
            val quoteResponse = readRandomQuoteService.readRandomQuote()
            if (!Quotes.QUOTES.contains(
                    QuoteValueObject(quoteResponse.quote, quoteResponse.quoter)
                )
            ) throw MismatchQuoteAndQuoterException
        }
    }
}
