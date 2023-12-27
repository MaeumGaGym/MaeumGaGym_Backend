package com.info.maeumgagym.global.config.swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    info = Info(
        title = "INFO : MaeumGaGym API 명세서",
        description = "헬스인을 위한 마음가짐",
        contact = Contact(name = "INFO", email = "example@gamil.com")
    )
)
@Configuration
class SwaggerConfig
