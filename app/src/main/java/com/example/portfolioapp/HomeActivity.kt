package com.example.portfolioapp

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mypart_pofo.PorflioManager
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity: AppCompatActivity() {

    lateinit var listview: ListView
    lateinit var homePrizeDBManager: CertificateManager
    lateinit var homePortDBManager: PorflioManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var certificateview: TextView
    lateinit var portfolioview: TextView

    lateinit var str_prizeTitle: String
    lateinit var str_prizeDate: String

    lateinit var str_portTitle: String
    lateinit var str_portstartDate : String
    lateinit var str_portendDate : String
    lateinit var str_portContent: String

    lateinit var nav_portfolio: ImageView
    lateinit var nav_home: ImageView
    lateinit var nav_certificate: ImageView


    var prizeList = ArrayList<ItemPrize>()
    var portList = ArrayList<ItemPort>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 리스트 속 textview 클릭시
        certificateview = findViewById(R.id.tv_certificateview)
        portfolioview = findViewById(R.id.tv_portfolioview)

        nav_portfolio = findViewById(R.id.nav_portfolio)
        nav_home = findViewById(R.id.nav_home)
        nav_certificate = findViewById(R.id.nav_certificate)

        certificateview.setOnClickListener{
            val intent = Intent(this,CertificateViewActivity::class.java)
            startActivity(intent)
        }
        portfolioview.setOnClickListener{
            val intent = Intent(this,PortfolioCalendarViewActivity::class.java)
            startActivity(intent)
        }
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





        // 자격증/수상 요약부분
        homePrizeDBManager = CertificateManager(this, "certificate", null, 1)
        sqlitedb = homePrizeDBManager.readableDatabase

        var cursor1: Cursor
        cursor1 = sqlitedb.rawQuery("SELECT * FROM certificate;", null)

        while (cursor1.moveToNext()) {
            do {
                str_prizeTitle = cursor1.getString(cursor1.getColumnIndex("name")).toString()
                str_prizeDate = cursor1.getString(cursor1.getColumnIndex("date")).toString()

                prizeList.add(ItemPrize(str_prizeTitle, str_prizeDate))
            } while (cursor1.moveToNext())
        }

        val prizeAdapter = PrizeListAdapter(this, prizeList)
        val prizeListView = findViewById<ListView>(R.id.homeprizeListView)
        prizeListView.adapter = prizeAdapter



        // 포트폴리오 요약부분
        homePortDBManager = PorflioManager(this, "portfolio", null, 1)
        sqlitedb = homePortDBManager.readableDatabase

        var cursor2: Cursor
        cursor2 = sqlitedb.rawQuery("SELECT * FROM portfolio;", null)

        while (cursor2.moveToNext()) {
            do {
                str_portTitle = cursor2.getString(cursor2.getColumnIndex("name")).toString()
                str_portstartDate = cursor2.getString(cursor2.getColumnIndex("startDate")).toString()
                str_portendDate = cursor2.getString(cursor2.getColumnIndex("EndDate")).toString()
                str_portContent = cursor2.getString(cursor2.getColumnIndex("content")).toString()

                portList.add(ItemPort(str_portTitle, str_portstartDate, str_portendDate, str_portContent))
            } while (cursor2.moveToNext())
        }

        val portAdapter = PortListAdapter(this, portList)
        val portListView = findViewById<ListView>(R.id.homeportListView)
        portListView.adapter = portAdapter



        // 리스트뷰 항목 클릭시
        listview = findViewById<ListView>(R.id.homeprizeListView)
        listview.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, CertificateListActivity::class.java)
            intent.putExtra("intent_name", str_prizeTitle)
            startActivity(intent)
        }

    }
}
