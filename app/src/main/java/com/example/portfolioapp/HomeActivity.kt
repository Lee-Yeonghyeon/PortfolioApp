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

    lateinit var certlistview: ListView
    lateinit var prizelistview: ListView
    lateinit var portlistview: ListView

    lateinit var homeCertDBManager: CertificateManager
    lateinit var homePrizeDBManager: PrizeManager
    lateinit var homePortDBManager: PorflioManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var certificateview: TextView
    lateinit var portfolioview: TextView
    lateinit var prizeview: TextView

    lateinit var str_certiTitle: String
    lateinit var str_certiDate: String
    lateinit var str_prizeTitle: String
    lateinit var str_prizeDate: String

    lateinit var str_portTitle: String
    lateinit var str_portstartDate: String
    lateinit var str_portendDate: String
    lateinit var str_portContent: String

    lateinit var nav_portfolio: ImageView
    lateinit var nav_home: ImageView
    lateinit var nav_certificate: ImageView

    lateinit var btn_writePort: ImageButton
    lateinit var btn_writePrize: ImageButton
    lateinit var btn_writeCert: ImageButton


    var prizeList = ArrayList<ItemPrize>()
    var portList = ArrayList<ItemPort>()
    var certList = ArrayList<ItemCertificate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        certificateview = findViewById(R.id.tv_certificateview)
        portfolioview = findViewById(R.id.tv_portfolioview)
        prizeview = findViewById(R.id.tv_prizeview)

        nav_portfolio = findViewById(R.id.nav_portfolio)
        nav_home = findViewById(R.id.nav_home)
        nav_certificate = findViewById(R.id.nav_certificate)

        btn_writePort = findViewById(R.id.btn_writePort)
        btn_writePrize = findViewById(R.id.btn_writePrize)
        btn_writeCert = findViewById(R.id.btn_writeCert)


        // 리스트뷰 안 textview 클릭시
        certificateview.setOnClickListener {
            val intent = Intent(this, CertificateViewActivity::class.java)
            startActivity(intent)
        }
        prizeview.setOnClickListener {
            val intent = Intent(this, CertificateViewActivity::class.java)
            startActivity(intent)
        }
        portfolioview.setOnClickListener {
            val intent = Intent(this, PortfolioCalendarViewActivity::class.java)
            startActivity(intent)
        }


        // 아래 버튼 3개
        nav_portfolio.setOnClickListener {
            val intent = Intent(this, PortfolioCalendarViewActivity::class.java)
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


        // 리스트뷰 텍스트 옆 글쓰러가기
        btn_writePort.setOnClickListener {
            val intent = Intent(this, WritePortfolioActivity::class.java)
            startActivity(intent)
        }
        btn_writePrize.setOnClickListener {
            val intent = Intent(this, WritePrizeActivity::class.java)
            startActivity(intent)
        }
        btn_writeCert.setOnClickListener {
            val intent = Intent(this, WriteCertificateActivity::class.java)
            startActivity(intent)
        }



        // 자격증 요약부분
        homeCertDBManager = CertificateManager(this, "certificate", null, 1)
        sqlitedb = homeCertDBManager.readableDatabase

        var cursor1: Cursor
        cursor1 = sqlitedb.rawQuery("SELECT * FROM certificate;", null)

        while (cursor1.moveToNext()) {
            do {
                str_certiTitle = cursor1.getString(cursor1.getColumnIndex("name")).toString()
                str_certiDate = cursor1.getString(cursor1.getColumnIndex("date")).toString()

                certList.add(0, ItemCertificate(str_certiTitle, str_certiDate))
            } while (cursor1.moveToNext())
        }

        val certAdapter = CertificateListAdapter(this, certList)
        val certificateListView = findViewById<ListView>(R.id.homecertListView)
        certificateListView.adapter = certAdapter


        // 자격증 리스트뷰 항목 클릭시
        certlistview = findViewById<ListView>(R.id.homecertListView)
        certlistview.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, CertificateListActivity::class.java)
            intent.putExtra("intent_name", str_certiTitle)
            startActivity(intent)
        }




        // 수상 요약부분
        homePrizeDBManager = PrizeManager(this, "prize", null, 1)
        sqlitedb = homePrizeDBManager.readableDatabase

        var cursor2: Cursor
        cursor2 = sqlitedb.rawQuery("SELECT * FROM prize;", null)

        while (cursor2.moveToNext()) {
            do {
                str_prizeTitle = cursor2.getString(cursor2.getColumnIndex("name")).toString()
                str_prizeDate = cursor2.getString(cursor2.getColumnIndex("date")).toString()

                prizeList.add(0, ItemPrize(str_prizeTitle, str_prizeDate))
            } while (cursor2.moveToNext())
        }

        val prizeAdapter = PrizeListAdapter(this, prizeList)
        val prizeListView = findViewById<ListView>(R.id.homeprizeListView)
        prizeListView.adapter = prizeAdapter


        // 수상 리스트뷰 항목 클릭시
        prizelistview = findViewById<ListView>(R.id.homeprizeListView)
        prizelistview.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, PrizeListActivity::class.java)
            intent.putExtra("intent_name", str_prizeTitle)
            startActivity(intent)
        }




        // 활동 요약부분
        homePortDBManager = PorflioManager(this, "portfolio", null, 1)
        sqlitedb = homePortDBManager.readableDatabase

        var cursor3: Cursor
        cursor3 = sqlitedb.rawQuery("SELECT * FROM portfolio;", null)

        while (cursor3.moveToNext()) {
            do {
                str_portTitle = cursor3.getString(cursor3.getColumnIndex("name")).toString()
                str_portstartDate = cursor3.getString(cursor3.getColumnIndex("startDate")).toString()
                str_portendDate = cursor3.getString(cursor3.getColumnIndex("EndDate")).toString()
                str_portContent = cursor3.getString(cursor3.getColumnIndex("content")).toString()

                portList.add(0, ItemPort(str_portTitle, str_portstartDate, str_portendDate, str_portContent))
            } while (cursor3.moveToNext())
        }

        val portAdapter = PortListAdapter(this, portList)
        val portListView = findViewById<ListView>(R.id.homeportListView)
        portListView.adapter = portAdapter




        // 활동 리스트뷰 항목 클릭시
        portlistview = findViewById<ListView>(R.id.homeportListView)
        portlistview.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, PortfolioViewActivity::class.java)
            intent.putExtra("intent_name", str_portTitle)
            startActivity(intent)
        }
    }
}
