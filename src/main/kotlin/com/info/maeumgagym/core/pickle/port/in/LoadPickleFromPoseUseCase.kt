package com.info.maeumgagym.core.pickle.port.`in`

import com.info.maeumgagym.core.pickle.dto.response.PickleListResponse

interface LoadPickleFromPoseUseCase {

    fun loadAllPagedFromPose(poseId: Long, idx: Int, size: Int): PickleListResponse
}
