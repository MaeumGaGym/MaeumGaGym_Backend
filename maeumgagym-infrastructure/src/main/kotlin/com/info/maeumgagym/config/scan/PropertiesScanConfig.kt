package com.info.maeumgagym.config.scan

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@ConfigurationPropertiesScan(basePackages = ["com.info.maeumgagym.**.env", "com.info.maeumgagym.env"])
@Configuration
class PropertiesScanConfig
