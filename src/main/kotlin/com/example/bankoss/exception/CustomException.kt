package com.example.bankoss.exception

open class CustomException(errorCode: ErrorCode
) : RuntimeException(errorCode.message) {

}