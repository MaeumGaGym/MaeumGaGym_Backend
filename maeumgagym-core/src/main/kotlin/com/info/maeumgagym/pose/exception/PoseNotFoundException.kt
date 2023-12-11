package com.info.maeumgagym.pose.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object PoseNotFoundException : MaeumGaGymException(ErrorCode.POSE_NOT_FOUND)
