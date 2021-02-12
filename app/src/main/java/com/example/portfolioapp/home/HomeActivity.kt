package com.example.portfolioapp.home

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.portfolioapp.*
import com.example.portfolioapp.certificate.CertificateListActivity
import com.example.portfolioapp.certificate.CertificateManager
import com.example.portfolioapp.certificate.WriteCertificateActivity
import com.example.portfolioapp.certprizeFullView.CertificateViewActivity
import com.example.portfolioapp.portFullView.PortfolioFullViewActivity
import com.example.portfolioapp.portfolio.PorflioManager
import com.example.portfolioapp.portfolio.PortfolioViewActivity
import com.example.portfolioapp.portfolio.WritePortfolioActivity
import com.example.portfolioapp.prize.PrizeListActivity
import com.example.portfolioapp.prize.PrizeManager
import com.example.portfolioapp.prize.WritePrizeActivity
import kotlin.collections.ArrayList

// 홈 화면 ( 최신 작성 글 요약 모아보기 )
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

    // 리스트뷰 항목에 데이터를 넣을 ArrayList 선언, 초기화
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


        // '활동, 자격증, 수상' 텍스트 클릭 시 각각의 모아보기 View로 이동
        certificateview.setOnClickListener {
            val intent = Intent(this, CertificateViewActivity::class.java)
            startActivity(intent)
        }
        prizeview.setOnClickListener {
            val intent = Intent(this, CertificateViewActivity::class.java)
            startActivity(intent)
        }
        portfolioview.setOnClickListener {
            val intent = Intent(this, PortfolioFullViewActivity::class.java)
            startActivity(intent)
        }


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


        // 연필 표시를 누르면 각각의 글쓰기로 이동
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



        /*
        자격증 - 최신 글 모아보기 (ListView)
        DB에서 값을 받아와 list에 넣어주는 코드
         */
        homeCertDBManager = CertificateManager(this, "certificate", null, 1)
        sqlitedb = homeCertDBManager.readableDatabase

        var cursor1: Cursor
        cursor1 = sqlitedb.rawQuery("SELECT * FROM certificate;", null)


        while (cursor1.moveToNext()) {
            do {
                str_certiTitle = cursor1.getString(cursor1.getColumnIndex("name")).toString()
                str_certiDate = cursor1.getString(cursor1.getColumnIndex("date")).toString()

                certList.add(0, ItemCertificate(str_certiTitle, str_certiDate))     // 인덱스 값을 0으로 줘 위로 쌓이도록 함
            } while (cursor1.moveToNext())
        }


        // 자격증 - 어댑터 초기화 및 리스트뷰와 연결
        val certAdapter = CertificateListAdapter(this, certList)
        val certificateListView = findViewById<ListView>(R.id.homecertListView)
        certificateListView.adapter = certAdapter


        // 자격증 - 리스트뷰 항목 클릭시 해당 글 보러가기
        certlistview = findViewById<ListView>(R.id.homecertListView)
        certlistview.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, CertificateListActivity::class.java)
            intent.putExtra("intent_name", str_certiTitle)
            startActivity(intent)
        }



        /*
        수상 - 최신 글 모아보기 (ListView)
        DB에서 값을 받아와 list에 넣어주는 코드
        */
        homePrizeDBManager = PrizeManager(this, "prize", null, 1)
        sqlitedb = homePrizeDBManager.readableDatabase

        var cursor2: Cursor
        cursor2 = sqlitedb.rawQuery("SELECT * FROM prize;", null)

        while (cursor2.moveToNext()) {
            do {
                str_prizeTitle = cursor2.getString(cursor2.getColumnIndex("name")).toString()
                str_prizeDate = cursor2.getString(cursor2.getColumnIndex("date")).toString()

                prizeList.add(0, ItemPrize(str_prizeTitle, str_prizeDate))  // 인덱스 값을 0으로 줘 위로 쌓이도록 함
            } while (cursor2.moveToNext())
        }


        // 수상 - 어댑터 초기화 및 리스트뷰와 연결
        val prizeAdapter = PrizeListAdapter(this, prizeList)
        val prizeListView = findViewById<ListView>(R.id.homeprizeListView)
        prizeListView.adapter = prizeAdapter


        // 수상 - 리스트뷰 항목 클릭시 해당 글 보러가기
        prizelistview = findViewById<ListView>(R.id.homeprizeListView)
        prizelistview.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, PrizeListActivity::class.java)
            intent.putExtra("intent_name", str_prizeTitle)
            startActivity(intent)
        }



        /*
        활동 - 최신 글 모아보기 (ListView)
        DB에서 값을 받아와 list에 넣어주는 코드
        */
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

                portList.add(0, ItemPort(str_portTitle, str_portstartDate, str_portendDate, str_portContent))   // 인덱스 값을 0으로 줘 위로 쌓이도록 함
            } while (cursor3.moveToNext())
        }

        // 활동 - 어댑터 초기화 및 리스트뷰와 연결
        val portAdapter = PortListAdapter(this, portList)
        val portListView = findViewById<ListView>(R.id.homeportListView)
        portListView.adapter = portAdapter

        // 활동 - 리스트뷰 항목 클릭시 해당 글 보러가기
        portlistview = findViewById<ListView>(R.id.homeportListView)
        portlistview.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, PortfolioViewActivity::class.java)
            intent.putExtra("intent_name", str_portTitle)
            startActivity(intent)
        }
    }
}
