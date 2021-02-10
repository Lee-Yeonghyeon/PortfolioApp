package com.example.portfolioapp

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.example.mypart_pofo.PorflioManager
import java.text.SimpleDateFormat

class PortfolioCalendarViewActivity : AppCompatActivity() {

    lateinit var calendarViewDBManager: PorflioManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var calendarView: CalendarView
    lateinit var tvDate: TextView

    lateinit var str_rTitle: String
    lateinit var str_rStartDate: String
    lateinit var str_rEndDate: String
    lateinit var str_rContent: String

    lateinit var nav_portfolio: ImageView
    lateinit var nav_home: ImageView
    lateinit var nav_certificate: ImageView

    var recordList = ArrayList<ItemRecord>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio_calendar_view)

        calendarView = findViewById<CalendarView>(R.id.calendarView)
        tvDate = findViewById<TextView>(R.id.tv_date)

        nav_portfolio = findViewById(R.id.nav_portfolio)
        nav_home = findViewById(R.id.nav_home)
        nav_certificate = findViewById(R.id.nav_certificate)

        nav_portfolio.setOnClickListener {
            val intent = Intent(this,PortfolioCalendarViewActivity::class.java)
            startActivity(intent)
        }
        nav_home.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }
        nav_certificate.setOnClickListener {
            val intent = Intent(this,CertificateViewActivity::class.java)
            startActivity(intent)
        }

        // 캘린더뷰 최소, 최대 활성화 날짜
        //calendarView.minDate = SimpleDateFormat("yyyyMMdd").parse("20000101").time
        calendarView.maxDate = SimpleDateFormat("yyyyMMdd").parse("20991231").time


        // 캘린더뷰의 날짜를 클릭했을 때 발생될 이벤트
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            tvDate.text = "${year}년 ${month + 1}월 ${dayOfMonth}일"

            //String.format("%d / %d / %d", year, month + 1, dayOfMonth)
            // year.toString() + month.toString() + dayOfMonth.toString()
        }



        // 달력 아래 일정 표시 부분
        calendarViewDBManager = PorflioManager(this, "portfolio", null, 1)
        sqlitedb = calendarViewDBManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM portfolio;", null)

        while (cursor.moveToNext()) {
            do {
                str_rTitle = cursor.getString(cursor.getColumnIndex("name")).toString()
                str_rStartDate = cursor.getString(cursor.getColumnIndex("startDate")).toString()
                str_rEndDate = cursor.getString(cursor.getColumnIndex("EndDate")).toString()
                str_rContent = cursor.getString(cursor.getColumnIndex("content")).toString()


                // if(str_rStartDate == tvDate.toString()) {
                recordList.add(ItemRecord(str_rTitle, str_rStartDate, str_rEndDate, str_rContent))
                // }else continue
            } while (cursor.moveToNext())
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