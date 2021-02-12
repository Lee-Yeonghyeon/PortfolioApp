package com.example.portfolioapp.portCalendar

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.example.portfolioapp.*
import com.example.portfolioapp.certprizeFullView.CertificateViewActivity
import com.example.portfolioapp.home.HomeActivity
import com.example.portfolioapp.portFullView.PortfolioFullViewActivity
import com.example.portfolioapp.portfolio.PorflioManager
import com.example.portfolioapp.portfolio.PortfolioViewActivity
import com.example.portfolioapp.portfolio.WritePortfolioActivity
import java.text.SimpleDateFormat

// 달력화면, 날짜 별 모아보기
class PortfolioCalendarViewActivity : AppCompatActivity() {

    lateinit var calendarViewDBManager: PorflioManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var recordlistview: ListView
    lateinit var calendarView: CalendarView
    lateinit var tvDate: TextView

    lateinit var str_rTitle: String
    lateinit var str_rStartDate: String
    lateinit var str_rEndDate: String
    lateinit var str_rContent: String

    var tvDate2: String = ""

    lateinit var nav_portfolio: ImageView
    lateinit var nav_home: ImageView
    lateinit var nav_certificate: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio_calendar_view)

        calendarView = findViewById<CalendarView>(R.id.calendarView)
        tvDate = findViewById<TextView>(R.id.tv_date)

        nav_portfolio = findViewById(R.id.nav_portfolio)
        nav_home = findViewById(R.id.nav_home)
        nav_certificate = findViewById(R.id.nav_certificate)


        // 하단 '활동, 홈, 자격증/수상'의 Imageview 클릭 시 각각의 모아보기 View로 이동
        nav_portfolio.setOnClickListener {
            val intent = Intent(this, PortfolioFullViewActivity::class.java)
            startActivity(intent)
        }
        nav_home.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        nav_certificate.setOnClickListener {
            val intent = Intent(this, CertificateViewActivity::class.java)
            startActivity(intent)
        }

        //뒤로가기
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        // 캘린더뷰 최소, 최대 활성화 날짜 설정
        calendarView.minDate = SimpleDateFormat("yyyyMMdd").parse("20000101").time
        calendarView.maxDate = SimpleDateFormat("yyyyMMdd").parse("20991231").time


        /*
        캘린더 뷰를 클릭했을 때
        - 누른 날짜에 해당하는 활동만 리스트뷰에 추가
         */
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            tvDate.text = "${year}년 ${month + 1}월 ${dayOfMonth}일"
            tvDate2 = tvDate.text.toString()
            str_rStartDate = tvDate2

            // 누를 때 마다 리스트뷰 내용이 초기화 된 후 추가해야 하므로 클릭 이벤트 안에 arrayList를 선언
            val recordList = ArrayList<ItemRecord>()


            // Date값을 기준으로 DB에서 값을 받아와 list에 넣어주는 코드
            calendarViewDBManager = PorflioManager(this, "portfolio", null, 1)
            sqlitedb = calendarViewDBManager.readableDatabase

            var cursor: Cursor
            cursor = sqlitedb.rawQuery("SELECT * FROM portfolio WHERE startDate ='" + str_rStartDate + "';", null)

            while (cursor.moveToNext()) {
                cursor.moveToFirst()
                do {
                    str_rTitle = cursor.getString(cursor.getColumnIndex("name")).toString()
                    // str_rStartDate = cursor.getString(cursor.getColumnIndex("startDate")).toString()
                    str_rEndDate = cursor.getString(cursor.getColumnIndex("EndDate")).toString()
                    str_rContent = cursor.getString(cursor.getColumnIndex("content")).toString()

                    recordList.add(ItemRecord(str_rTitle, str_rStartDate, str_rEndDate, str_rContent))
                } while (cursor.moveToNext())
            }


            // 어댑터 초기화 및 리스트뷰와 연결
            val recordAdapter = RecordListAdapter(this, recordList)
            val recordListView = findViewById<ListView>(R.id.recordListView)
            recordListView.adapter = recordAdapter


            // 리스트뷰 항목 클릭시 해당 글 보러가기
            recordlistview = findViewById<ListView>(R.id.recordListView)
            recordlistview.setOnItemClickListener { parent, view, position, id ->
                val intent = Intent(this, PortfolioViewActivity::class.java)
                intent.putExtra("intent_name", str_rTitle)
                startActivity(intent)
            }

            cursor.close()
            sqlitedb.close()
            calendarViewDBManager.close()
        }


    }

    // 옵션 메뉴 활성화
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.forcalendarview, menu)
        return true
    }


    // 옵션 메뉴 클릭시 각각 활동 추가하기(WritePortfolioActivity), 활동 모아보기(PortfolioFullViewActivity)로 이동
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_write_portfolio -> {
                val intent = Intent(this, WritePortfolioActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_portfullview -> {
                val intent = Intent(this, PortfolioFullViewActivity::class.java)
                startActivity(intent)
                return true
            }
            android.R.id.home ->{
                val intent = Intent(this,PortfolioFullViewActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}


