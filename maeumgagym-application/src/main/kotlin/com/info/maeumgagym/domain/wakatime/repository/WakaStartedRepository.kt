package com.info.maeumgagym.domain.wakatime.repository

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.wakatime.entity.WakaStartedJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Repository
interface WakaStartedRepository: JpaRepository<WakaStartedJpaEntity, UUID> {

    @Query(
        value = "SELECT waka " +
            "FROM ${TableNames.WAKA_START_TABLE} waka " +
            "JOIN ${TableNames.USER_TABLE} user ON user.id = waka.id " +
            "WHERE user.is_deleted = false " +
            "AND user.last_saved = :lastSaved",
        nativeQuery = true
    )
    fun findAllWakaStartedByUserAndState(@Param("lastSaved") lastSaved: LocalDate): MutableList<WakaStartedJpaEntity>

    @Query(
        value = "UPDATE ${TableNames.WAKA_START_TABLE} waka, ${TableNames.USER_TABLE} user " +
            "SET waka.start_at = :startAt, user.last_saved = :lastSaved, user.waka = :waka " +
            "WHERE user.id = waka.id",
        nativeQuery = true
    )
    fun renewalAllWakaStarted(
        @Param("startAt") startAt: LocalDateTime,
        @Param("lastSaved") lastSaved: LocalDateTime,
        @Param("waka") waka: Long
    )
}
