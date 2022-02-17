package com.example.bankoss.exception.user

import com.example.bankoss.exception.CustomException
import com.example.bankoss.exception.ErrorCode

class UserEmailDuplicateException : CustomException(ErrorCode.DUPLICATE_USER_EMAIL) {
    fun getErrorCode() : ErrorCode{
        return ErrorCode.DUPLICATE_USER_EMAIL
    }
}