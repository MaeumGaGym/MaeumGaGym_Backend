package com.info.maeumgagym.pickle.port.`in`

import com.info.maeumgagym.pickle.dto.response.PickleListResponse

interface LoadPicklePoseUseCase {

    fun loadAllPagedFromPose(poseId: Long, idx: Int, size: Int): PickleListResponse
}
