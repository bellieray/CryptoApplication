package com.example.domain.model

sealed class Result<T> {
    class Success<T>(val data: T? = null) : Result<T>()
    class Failed<T>(val exception: String?) : Result<T>()
}