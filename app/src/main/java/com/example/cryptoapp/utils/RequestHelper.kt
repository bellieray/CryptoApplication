package com.example.cryptocurrency.utils

sealed class Result<T> {
    class Success<T>(val data: T) : Result<T>()
    class Failed<T>(val exception: String?) : Result<T>()
}