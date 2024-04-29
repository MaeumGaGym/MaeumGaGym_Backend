package com.info.maeumgagym

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
//@EnableAspectJAutoProxy(proxyTargetClass = true)
class MaeumGaGymApplication

fun main(args: Array<String>) {
    runApplication<MaeumGaGymApplication>(*args)
}
