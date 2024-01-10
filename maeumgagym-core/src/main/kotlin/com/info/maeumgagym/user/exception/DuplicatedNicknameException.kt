package com.info.maeumgagym.user.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object DuplicatedNicknameException : MaeumGaGymException(ErrorCode.DUPLICATED_NICKNAME)
