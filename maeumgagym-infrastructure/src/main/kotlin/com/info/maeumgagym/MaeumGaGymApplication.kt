package com.info.maeumgagym

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
class MaeumGaGymApplication

fun main(args: Array<String>) {
    runApplication<MaeumGaGymApplication>(*args)
}
