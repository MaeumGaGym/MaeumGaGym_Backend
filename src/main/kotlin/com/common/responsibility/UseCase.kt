package com.info.common.responsibility

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class])
@Service
annotation class UseCase()
