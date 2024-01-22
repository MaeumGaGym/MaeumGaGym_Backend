package com.info.common

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
@Service
annotation class UseCase()
