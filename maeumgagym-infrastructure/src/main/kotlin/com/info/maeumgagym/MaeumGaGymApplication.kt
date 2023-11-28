package com.info.maeumgagym

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan(basePackages = ["com.info.maeumgagym.global.env"])
@SpringBootApplication
class MaeumGaGymApplication

fun main(args: Array<String>) {
    runApplication<MaeumGaGymApplication>(*args)
}
