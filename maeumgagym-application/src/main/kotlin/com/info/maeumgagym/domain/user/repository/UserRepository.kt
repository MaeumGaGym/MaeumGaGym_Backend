package com.info.maeumgagym.domain.user.repository

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface UserRepository : JpaRepository<UserJpaEntity, UUID> {

    fun findByOauthId(oauthId: String): UserJpaEntity?

    @Query(
        value = "SELECT * FROM ${TableNames.USER_TABLE} u WHERE u.nickname = :nickname limit 1",
        nativeQuery = true
    )
    fun findByNicknameInNative(@Param("nickname") nickname: String): UserJpaEntity?

    @Query(
        value = "SELECT * FROM ${TableNames.USER_TABLE} u WHERE u.oauth_id = :oauthId limit 1",
        nativeQuery = true
    )
    fun findByOauthIdInNative(@Param("oauthId") oauthId: String): UserJpaEntity?

    @Query(
        value = "SELECT * FROM ${TableNames.USER_TABLE} u WHERE u.oauth_id = :oauthId and u.is_deleted = true limit 1",
        nativeQuery = true
    )
    fun findDeletedUserByOauthIdInNative(@Param("oauthId") oauthId: String): UserJpaEntity?

    @Query(
        value = "DELETE users, delete_at " +
            "FROM ${TableNames.USER_TABLE} users " +
            "JOIN ${TableNames.DELETED_AT_TABLE} delete_at ON users.id = delete_at.user_id " +
            "WHERE users.is_deleted = true " +
            "AND delete_at.date <= DATE_SUB(NOW(), INTERVAL 30 DAY)",
        nativeQuery = true
    )
    fun deleteAllWithdrawalUserOneMonthAgo(): MutableList<UserJpaEntity>
}
