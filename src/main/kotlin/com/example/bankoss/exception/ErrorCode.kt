package com.example.bankoss.exception

enum class ErrorCode(
    val status : Int,
    val message : String
) {
    /**
     * security
     */
    ACCESS_DENIED(4000, "접근이 제한되었습니다."),
    AUTHORIZATION_NOT_FOUND(4030, "Authorization 헤더를 찾을 수 없습니다."),
    NOT_FOUND_REFRESHTOKEN(4040, "token 이 없습니다."),
    EXPIRED_TOKEN(4010, "token 이 만료되었습니다."),
    INVALID_TOKEN(4000,"token이 잘못되었습니다." ),
    INVALID_SIGNATURE(4000, "잘못된 token 서명입니다."),
    UNSUPPORTED_TOKEN(4000, "지원되지않는 token 입니다."),

    /**
     * user
     */
    USER_NOT_FOUND(4040, "user를 찾을 수 없습니다."),
    DUPLICATE_USER(4000, "이미 가입된 유저입니다."),
    DUPLICATE_USER_EMAIL(4000, "중복된 이메일입니다."),
    FAIL_USER_LOGIN(4010, "로그인을 실패했습니다.")
}