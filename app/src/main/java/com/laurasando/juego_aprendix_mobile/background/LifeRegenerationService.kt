package com.laurasando.juego_aprendix_mobile.background

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.laurasando.juego_aprendix_mobile.data.network.LifeRegenerator
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class LifeRegenerationService : Service() {
    private val scheduler = Executors.newScheduledThreadPool(1)
    private var userId: String? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        userId = intent?.getStringExtra("userId")
        userId?.let {
            val task = LifeRegenerator(it)
            scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS)  // execute pass 1 minutes
        }
        return START_STICKY
    }

    override fun onDestroy() {
        scheduler.shutdown()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
