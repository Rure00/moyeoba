package com.moyeoba.moyeoba.data.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginResponse(
        @get:JsonProperty("isNewUser")
        @param:JsonProperty("isNewUser")
        val isNewUser: Boolean
)
