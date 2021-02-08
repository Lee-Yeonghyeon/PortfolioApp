package com.example.portfolioapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

class PortfolioCalendarViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio_calendar_view)

        supportActionBar?.setTitle("포트폴리오 월별 보기")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { //각각 포트폴리오 입력창, portfolioFullView창으로 이동할 수 있도록 연결해주세요(액션바에 있는 아이콘임)
        menuInflater.inflate(R.menu.forcalendarview, menu)
        return true
    }
}