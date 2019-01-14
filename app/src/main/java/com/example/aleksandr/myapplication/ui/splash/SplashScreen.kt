package com.example.aleksandr.myapplication.ui.splash

import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.os.Bundle
import android.app.Activity
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.login.LoginActivity


class Splash : AppCompatActivity() {

    /** Duration of wait  */
    private val SPLASH_DISPLAY_LENGTH = 1000L

    /** Called when the activity is first created.  */
    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.splash)

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        Handler().postDelayed(Runnable {
            /* Create an Intent that will start the Menu-Activity. */
            val mainIntent = Intent(this@Splash, LoginActivity::class.java)
            this@Splash.startActivity(mainIntent)
            this@Splash.finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}