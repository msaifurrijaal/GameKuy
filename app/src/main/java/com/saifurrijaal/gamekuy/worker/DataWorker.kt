package com.saifurrijaal.gamekuy.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.saifurrijaal.gamekuy.data.database.GameDatabase
import com.saifurrijaal.gamekuy.repository.GameRepository
import retrofit2.HttpException

class DataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val gameDao = GameDatabase.getInstance(applicationContext).gameDao()
        val repository = GameRepository(gameDao)

        try {
            repository.refreshGame()
            return Result.success()
        } catch (e : HttpException) {
            return Result.retry()
        }
    }

}