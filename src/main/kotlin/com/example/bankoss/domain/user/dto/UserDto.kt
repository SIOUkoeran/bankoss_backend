package com.example.bankoss.domain.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.sun.istack.NotNull
import javax.validation.constraints.Size

data class UserDto(
    @NotNull
    @Size(min = 3, max = 50)
    val username : String,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val password : String,

    @Size(min = 3, max = 50)
    val email : String
) {
}