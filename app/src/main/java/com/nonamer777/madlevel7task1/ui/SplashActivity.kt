package com.nonamer777.madlevel7task1.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nonamer777.madlevel7task1.R
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)


        Executors.newSingleThreadScheduledExecutor().schedule({
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
        }, 1_000, TimeUnit.MILLISECONDS)
    }
}
