package com.example.nojotoapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nojotoapp.R
import kotlinx.android.synthetic.main.activity_after_splash.*

class AfterSplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_splash)

        supportActionBar?.hide()

        english.setOnClickListener {
            val intent = Intent(this, LoginSignupScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}