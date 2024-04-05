package com.info.maeumgagym.global.vo

import com.info.maeumgagym.domain.pose.repository.PoseRepository
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class TestRepositories(
    private val poseRepository: PoseRepository
) {
    companion object {
        lateinit var poseRepository: PoseRepository
    }

    @Bean
    fun initial() {
        TestRepositories.poseRepository = poseRepository
    }
}
