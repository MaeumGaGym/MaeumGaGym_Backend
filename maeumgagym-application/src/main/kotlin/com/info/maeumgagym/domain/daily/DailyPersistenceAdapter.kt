package com.info.maeumgagym.domain.daily

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.daily.model.Daily
import com.info.maeumgagym.daily.port.out.DeleteDailyPort
import com.info.maeumgagym.daily.port.out.ReadDailyPort
import com.info.maeumgagym.daily.port.out.SaveDailyPort
import com.info.maeumgagym.domain.daily.mapper.DailyMapper
import com.info.maeumgagym.domain.daily.repository.DailyRepository
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.user.model.User
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@PersistenceAdapter
internal class DailyPersistenceAdapter(
    private val dailyRepository: DailyRepository,
    private val dailyMapper: DailyMapper,
    private val userMapper: UserMapper
) : SaveDailyPort, DeleteDailyPort, ReadDailyPort {

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(daily: Daily): Daily =
        dailyMapper.toDomain(
            dailyRepository.save(dailyMapper.toEntity(daily))
        )

    @Transactional(propagation = Propagation.MANDATORY)
    override fun delete(daily: Daily) {
        dailyRepository.delete(dailyMapper.toEntity(daily))
    }

    override fun readByUploaderAndDate(user: User, date: LocalDate): Daily? =
        dailyRepository.findByUploaderIdAndDate(user.id!!, date)
            ?.run { dailyMapper.toDomain(this) }

    override fun readAllByUploader(user: User, page: Int, size: Int): List<Daily> =
        dailyRepository.findAllByUploaderId(
            user.id!!,
            PageRequest.of(page, size, Sort.by("date").descending())
        ).map {
            dailyMapper.toDomain(it)
        }
}
