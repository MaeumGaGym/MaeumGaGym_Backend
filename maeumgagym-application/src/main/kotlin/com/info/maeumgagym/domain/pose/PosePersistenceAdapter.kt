package com.info.maeumgagym.domain.pose

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.pose.mapper.PoseMapper
import com.info.maeumgagym.domain.pose.repository.PoseRepository
import com.info.maeumgagym.pose.model.Pose
import com.info.maeumgagym.pose.port.out.ReadPosePort

@PersistenceAdapter
internal class PosePersistenceAdapter(
    private val poseRepository: PoseRepository,
    private val poseMapper: PoseMapper
) : ReadPosePort {

    override fun readById(id: Long): Pose? =
        poseRepository.findById(id)?.let {
            poseMapper.toDomain(it)
        }
}
