package com.info.maeumgagym.application.domain.user.repository

import com.info.maeumgagym.application.TableNames
import com.info.maeumgagym.application.domain.user.entity.UserJpaEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param
import java.util.*

@org.springframework.stereotype.Repository
interface UserNativeRepository : Repository<UserJpaEntity, UUID> {
    @Query(
        value = "SELECT * FROM ${TableNames.USER_TABLE} u WHERE u.nickname = :nickname limit 1",
        nativeQuery = true
    )
    fun findByNicknameOnWithdrawalSafe(@Param("nickname") nickname: String): UserJpaEntity?

    @Query(
        value = "SELECT * FROM ${TableNames.USER_TABLE} u WHERE u.oauth_id = :oauthId limit 1",
        nativeQuery = true
    )
    fun findByOauthIdOnWithdrawalSafe(@Param("oauthId") oauthId: String): UserJpaEntity?

    @Query(
        value = "SELECT * FROM ${TableNames.USER_TABLE} u " +
            "WHERE u.oauth_id = :oauthId and u.is_deleted_at IS NOT NULL limit 1",
        nativeQuery = true
    )
    fun findDeletedUserByOauthId(@Param("oauthId") oauthId: String): UserJpaEntity?

    @Query(
        value = "DELETE u " +
            "FROM ${TableNames.USER_TABLE} u " +
            "WHERE u.is_deleted_at IS NOT NULL " +
            "AND u.is_deleted_at <= DATE_SUB(NOW(), INTERVAL 30 DAY)",
        nativeQuery = true
    )
    fun deleteAllWithdrawalUserOneMonthAgo(): MutableList<UserJpaEntity>

    @Query(
        value = "SELECT * FROM ${TableNames.USER_TABLE} u WHERE u.waka_started_at IS NOT NULL",
        nativeQuery = true
    )
    fun findAllByWakaStartedAtNotNullOnWithdrawalSafe(): MutableList<UserJpaEntity>
}
