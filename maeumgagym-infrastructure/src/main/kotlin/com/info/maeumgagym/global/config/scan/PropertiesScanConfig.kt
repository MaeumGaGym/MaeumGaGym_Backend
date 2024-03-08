package com.info.maeumgagym.global.config.scan

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@ConfigurationPropertiesScan(basePackages = ["com.info.maeumgagym"])
@Configuration
class PropertiesScanConfig
