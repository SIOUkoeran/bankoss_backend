package com.example.bankoss.domain.user.dto

data class UserLoginResponse(
    val username : String,
    val id : Long,
    val email : String
) {

}