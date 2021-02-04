package com.example.portfolioapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class WritePrizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_prize)

        supportActionBar?.setTitle("수상내역 입력하기")
    }
}