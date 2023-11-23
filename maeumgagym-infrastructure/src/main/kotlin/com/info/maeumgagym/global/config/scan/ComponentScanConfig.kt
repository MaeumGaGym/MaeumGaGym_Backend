package com.info.maeumgagym.global.config.scan

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(
    basePackages = ["com.info.maeumgagym"],
)
class ComponentScanConfig
