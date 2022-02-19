package com.example.bankoss.domain.user.dto

data class TokenDto(
    val access_token : String,
    val refresh_token: String
) {
}