package com.example.domain.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.repository.CryptoCurrencyRepository
import com.example.domain.utils.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CryptoWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val repository: CryptoCurrencyRepository
) :
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val response = repository.detectPriceChange()
            response?.name?.let {
                NotificationHelper.showNotification(applicationContext, "CryptoCurrency", it)
            }
            Log.e("CryptoWorkerSuccess", "Success")
            return Result.success()
        } catch (e: Exception) {
            Log.e("WorkerFailed", e.localizedMessage)
            Result.failure()
        }

    }
}
