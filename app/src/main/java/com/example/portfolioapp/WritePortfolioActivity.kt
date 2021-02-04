package com.example.portfolioapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class WritePortfolioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_portfolio)

        supportActionBar?.setTitle("포트폴리오 입력하기")
    }
}