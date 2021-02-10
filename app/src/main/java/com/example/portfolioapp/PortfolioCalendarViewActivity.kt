package com.example.portfolioapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CalendarView
import android.widget.ListView
import java.text.SimpleDateFormat

class PortfolioCalendarViewActivity : AppCompatActivity() {

    lateinit var calendarView: CalendarView
    var recordList = arrayListOf<ItemRecord>(
            ItemRecord("GURU2 해커톤", "2021.02.01 ~ 2021.02.14", "최우수상받고싶어요"),
            ItemRecord("00공모전", "2021.02.02 ~ 2021.02.04", "열심히하자"),
            ItemRecord("대외활동", "2021.02.04 ~ 2021.03.17", "하하하")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio_calendar_view)

        calendarView = findViewById<CalendarView>(R.id.calendarView)


        // 캘린더뷰 최소, 최대 활성화 날짜
        calendarView.minDate = SimpleDateFormat("yyyyMMdd").parse("20000101").time
        calendarView.maxDate = SimpleDateFormat("yyyyMMdd").parse("20991231").time

        // 캘린더뷰의 날짜를 클릭했을 때 발생될 이벤트
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->

        }

        val recordAdapter = RecordListAdapter(this, recordList)
        val recordListView = findViewById<ListView>(R.id.recordListView)
        recordListView.adapter = recordAdapter


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.forcalendarview, menu)
        return true
    }


    // 메뉴 클릭, 액티비티 전환
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_write_portfolio -> {
                val intent = Intent(this, WritePortfolioActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_portfullview-> {
                val intent = Intent(this,PortfolioFullViewActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}