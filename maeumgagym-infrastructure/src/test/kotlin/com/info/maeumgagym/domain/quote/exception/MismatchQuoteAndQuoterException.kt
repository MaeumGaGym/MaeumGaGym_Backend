package com.info.maeumgagym.domain.quote.exception

import com.info.maeumgagym.error.MaeumGaGymTestException
import com.info.maeumgagym.error.TestErrorCode

internal object MismatchQuoteAndQuoterException : MaeumGaGymTestException(TestErrorCode.MISMATCH_QUOTE_AND_QUOTER)
