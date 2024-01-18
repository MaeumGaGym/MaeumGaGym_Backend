package com.info.maeumgagym.domain.quote.exception

import com.info.maeumgagym.global.MaeumGaGymTestException
import com.info.maeumgagym.global.TestErrorCode

internal object MismatchQuoteAndQuoterException : MaeumGaGymTestException(TestErrorCode.MISMATCH_QUOTE_AND_QUOTER)
