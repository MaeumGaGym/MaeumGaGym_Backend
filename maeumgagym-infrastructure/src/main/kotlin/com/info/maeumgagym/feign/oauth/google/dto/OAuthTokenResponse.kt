package com.info.maeumgagym.feign.oauth.google.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OAuthTokenResponse (
    @JsonProperty("access_token")
    val accessToken: String
)
