package com.info.maeumgagym.application.domain.daily

import com.info.maeumgagym.common.annotation.responsibility.PersistenceAdapter
import com.info.maeumgagym.application.domain.daily.mapper.DailyMapper
import com.info.maeumgagym.application.domain.daily.repository.DailyRepository
import com.info.maeumgagym.core.daily.port.out.DeleteDailyPort
import com.info.maeumgagym.core.daily.port.out.ReadDailyPort
import com.info.maeumgagym.core.daily.port.out.SaveDailyPort
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@PersistenceAdapter
internal class DailyPersistenceAdapter(
    private val dailyRepository: DailyRepository,
    private val dailyMapper: DailyMapper
) : SaveDailyPort, DeleteDailyPort, ReadDailyPort {

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(daily: com.info.maeumgagym.core.daily.model.Daily): com.info.maeumgagym.core.daily.model.Daily =
        dailyMapper.toDomain(
            dailyRepository.save(dailyMapper.toEntity(daily))
        )

    @Transactional(propagation = Propagation.MANDATORY)
    override fun delete(daily: com.info.maeumgagym.core.daily.model.Daily) {
        dailyRepository.delete(dailyMapper.toEntity(daily))
    }

    override fun readByUploaderAndDate(
        user: com.info.maeumgagym.core.user.model.User,
        date: LocalDate
    ): com.info.maeumgagym.core.daily.model.Daily? =
        dailyRepository.findByUploaderIdAndDate(user.id!!, date)
            ?.run { dailyMapper.toDomain(this) }

    override fun readAllByUploader(
        user: com.info.maeumgagym.core.user.model.User,
        page: Int,
        size: Int
    ): List<com.info.maeumgagym.core.daily.model.Daily> =
        dailyRepository.findAllByUploaderId(
            user.id!!,
            PageRequest.of(page, size, Sort.by("date").descending())
        ).map {
            dailyMapper.toDomain(it)
        }
}
