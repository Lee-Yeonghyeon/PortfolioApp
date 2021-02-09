package com.example.portfolioapp.HomeActivity

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.portfolioapp.R

class HomeActivity: AppCompatActivity() {

    var prizeList = arrayListOf<ItemPrize>(
        ItemPrize("컴퓨터활용능력 1급", "2020.02.05"),
        ItemPrize("Programming Guru2 최우수상", "2021.02.18")
    )

    var portList = arrayListOf<ItemPort>(
        ItemPort("GURU2 해커톤", "2021.02.01 ~ 2021.02.14", "최우수상받고싶어요"),
        ItemPort("00공모전", "2021.02.02 ~ 2021.02.04", "열심히하자"),
        ItemPort("대외활동", "2021.02.04 ~ 2021.03.17", "하하하"),
        ItemPort("GURU2 해커톤", "2021.02.01 ~ 2021.02.14", "최우수상받고싶어요"),
        ItemPort("00공모전", "2021.02.02 ~ 2021.02.04", "열심히하자"),
        ItemPort("대외활동", "2021.02.04 ~ 2021.03.17", "하하하")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val prizeAdapter = PrizeListAdapter(this, prizeList)
        val prizeListView =  findViewById<ListView>(R.id.homeprizeListView)
        prizeListView.adapter = prizeAdapter

        val portAdapter = PortListAdapter(this, portList)
        val portListView = findViewById<ListView>(R.id.homeportListView)
        portListView.adapter = portAdapter
    }

}