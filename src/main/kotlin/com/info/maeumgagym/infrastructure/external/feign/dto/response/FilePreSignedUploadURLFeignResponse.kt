package com.info.maeumgagym.infrastructure.external.feign.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class FilePreSignedUploadURLFeignResponse(

    @JsonProperty("uploadURL")
    val uploadURL: String,

    @JsonProperty("videoId")
    val videoId: String
)
