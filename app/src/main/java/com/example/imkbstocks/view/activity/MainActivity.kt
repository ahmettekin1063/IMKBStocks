package com.example.imkbstocks.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.imkbstocks.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        com.example.imkbstocks.mResources = resources
    }
}