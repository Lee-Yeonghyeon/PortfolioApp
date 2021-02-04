package com.example.portfolioapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class WriteDailyPortfolioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_daily_portfolio)

        supportActionBar?.setTitle("ㅇㅇ공모전 입력하기") //사용자가 선택한 공모전 이름이 타이틀로 바뀔 수 있게 수정해주세요
    }
}