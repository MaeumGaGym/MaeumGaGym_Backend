package com.info.maeumgagym.pickle.port.`in`

import com.info.maeumgagym.pickle.dto.response.PickleListResponse

interface LoadPickleFromPoseUseCase {

    fun loadAllPagedFromPose(poseId: Long, idx: Int, size: Int): PickleListResponse
}
