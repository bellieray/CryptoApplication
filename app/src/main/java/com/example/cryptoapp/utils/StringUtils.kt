package com.example.cryptoapp.utils

import java.util.regex.Pattern

object StringUtils {
    fun isEmailValid(email: String): VALIDATION {
        val pattern = Pattern.compile(
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'" +
                    "*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]" +
                    "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\" +
                    ".)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b" +
                    "\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",
            Pattern.CASE_INSENSITIVE
        )
        return if (pattern.matcher(email).matches()) VALIDATION.VALID else VALIDATION.INVALID
    }

    fun isPasswordValid(password: String): VALIDATION {
        return if (password.isBlank().not() && password.isEmpty()
                .not()
        ) VALIDATION.VALID else VALIDATION.INVALID
    }
}

enum class VALIDATION {
    VALID, INVALID
}