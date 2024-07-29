package com.example.jokehub.ui.component.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jokehub.ui.component.main.MainActivity
import java.util.Timer
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var login: Intent = Intent(this, MainActivity::class.java)
        Timer("SettingUp", false).schedule(1000) {
            startActivity(login)
            finish()
        }
    }
}