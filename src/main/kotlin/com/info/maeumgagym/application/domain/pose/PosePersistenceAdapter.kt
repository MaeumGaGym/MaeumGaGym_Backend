package com.info.maeumgagym.application.domain.pose

import com.info.maeumgagym.common.annotation.responsibility.PersistenceAdapter
import com.info.maeumgagym.application.domain.pose.mapper.PoseMapper
import com.info.maeumgagym.application.domain.pose.repository.PoseNativeRepository
import com.info.maeumgagym.application.domain.pose.repository.PoseRepository
import com.info.maeumgagym.core.pose.model.Pose
import com.info.maeumgagym.core.pose.port.out.ReadPosePort
import com.info.maeumgagym.core.pose.port.out.SavePosePort
import java.time.LocalDateTime

@PersistenceAdapter
internal class PosePersistenceAdapter(
    private val poseRepository: PoseRepository,
    private val poseNativeRepository: PoseNativeRepository,
    private val poseMapper: PoseMapper,
    private val getPoseLastModifiedAt: GetPoseLastModifiedAt
) : SavePosePort, ReadPosePort {

    override fun save(pose: Pose): Pose = poseMapper.toDomain(
        poseRepository.save(poseMapper.toEntity(pose))
    )

    override fun readAll(): List<Pose> = poseRepository.findAll().map {
        poseMapper.toDomain(it)
    }

    override fun getLastModifiedAt(): LocalDateTime =
        getPoseLastModifiedAt()

    override fun readById(id: Long): Pose? =
        poseRepository.findById(id)?.let {
            poseMapper.toDomain(it)
        }

    override fun readByTag(tag: String): List<Pose> =
        poseNativeRepository.findAllByTag(tag).map {
            poseMapper.toDomain(it)
        }

    override fun readAllRandomLimit30(): List<Pose> =
        poseNativeRepository.readAllRandomLimit30().map {
            poseMapper.toDomain(it)
        }
}
