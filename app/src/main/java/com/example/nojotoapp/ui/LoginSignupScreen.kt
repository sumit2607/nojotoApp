package com.example.nojotoapp.ui

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.nojotoapp.Adapters.ViewPagerAdapter
import com.example.nojotoapp.R
import com.example.nojotoapp.extensions.autoScroll
import kotlinx.android.synthetic.main.activity_login_signup_screen.*

class LoginSignupScreen : AppCompatActivity() {

    private lateinit var adapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup_screen)
        setAdapter()
        setViewPager()

        supportActionBar?.hide()

        btnFb.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun setAdapter() {

        this.adapter = ViewPagerAdapter(
            listOf(
                R.drawable.ic_one,
                R.drawable.ic_two,
                R.drawable.ic_three,
                R.drawable.ic_four,
                R.drawable.ic_five
            )
        )
    }

    private fun setViewPager() {
        viewPager.adapter = adapter

        /**
         * Start automatic scrolling with an
         * interval of 3 seconds.
         */
        viewPager.autoScroll(3000)
    }
}