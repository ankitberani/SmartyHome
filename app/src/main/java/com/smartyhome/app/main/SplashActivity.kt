package com.smartyhome.app.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.smartyhome.app.R
import com.smartyhome.app.main.home.menudrawer.MenuDrawerActivity
import com.smartyhome.app.main.login.LoginActivity
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.SharedPreference

class SplashActivity : AppCompatActivity() {
    lateinit var sharedPrefe: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharedPrefe = SharedPreference(this)
        redirect()
    }

    fun redirect() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(Runnable {
            if (sharedPrefe.getBoolean(Constant.DASH_SHOWN) == false) {
                val intent = Intent(this@SplashActivity, LandingActivity::class.java)
                startActivity(intent)
            } else if (sharedPrefe?.getLoginInfo() != null) {
                val intent = Intent(this@SplashActivity, MenuDrawerActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, Constant.SPLASH_DELAY)
    }
}