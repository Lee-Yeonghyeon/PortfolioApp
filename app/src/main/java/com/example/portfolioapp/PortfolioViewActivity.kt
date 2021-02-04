package com.example.portfolioapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

class PortfolioViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio_view)

        supportActionBar?.setTitle("ㅇㅇ 공모전") //사용자가 선택한 공모전 이름이 타이틀로 바뀔 수 있게 수정해주세요
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { //사용자가 선택한 공모전의 dailyPortfolio로 이동할 수 있도록 연결해주세요(액션바에 있는 아이콘임)
        menuInflater.inflate(R.menu.fordaily, menu)
        return true
    }
}