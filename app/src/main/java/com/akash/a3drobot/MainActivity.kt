package com.akash.a3drobot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myView = MyView(this)
        supportActionBar?.hide()
        setContentView(myView)
    }
}