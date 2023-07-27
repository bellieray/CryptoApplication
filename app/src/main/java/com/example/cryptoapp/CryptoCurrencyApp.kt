package com.example.cryptoapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.cryptoapp.utils.Constants.SYNC_DATA
import com.example.cryptoapp.worker.CryptoWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class CryptoCurrencyApp : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()
        initWorker()
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder().setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.DEBUG).build()


    private fun initWorker() {
        val workRequest = OneTimeWorkRequestBuilder<CryptoWorker>(
        ).setInitialDelay(20, TimeUnit.SECONDS).addTag(SYNC_DATA)
            .build()

        workManager.enqueue(
            workRequest
        )
    }
}