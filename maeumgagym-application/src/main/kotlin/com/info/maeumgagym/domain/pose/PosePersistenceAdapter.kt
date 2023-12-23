package com.info.maeumgagym.domain.pose

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.pose.mapper.PoseMapper
import com.info.maeumgagym.domain.pose.repository.PoseRepository
import com.info.maeumgagym.pose.model.Pose
import com.info.maeumgagym.pose.port.out.FindPoseByIdPort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@Transactional
@PersistenceAdapter
class PosePersistenceAdapter(
    private val poseRepository: PoseRepository,
    private val poseMapper: PoseMapper
) : FindPoseByIdPort {

    override fun findById(id: Long): Pose? = poseRepository.findByIdOrNull(id)?.let { poseMapper.toDomain(it) }
}
