package com.info.maeumgagym.domain.quote.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object MismatchQuoteAndQuoterException : MaeumGaGymException(ErrorCode.MISMATCH_QUOTE_AND_QUOTER)
