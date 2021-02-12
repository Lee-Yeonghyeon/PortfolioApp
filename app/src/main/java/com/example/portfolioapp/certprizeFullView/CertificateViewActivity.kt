package com.example.portfolioapp.certprizeFullView

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.portfolioapp.R
import com.example.portfolioapp.certificate.CertificateListActivity
import com.example.portfolioapp.certificate.CertificateManager
import com.example.portfolioapp.certificate.WriteCertificateActivity
import com.example.portfolioapp.portCalendar.PortfolioCalendarViewActivity
import com.example.portfolioapp.home.HomeActivity
import com.example.portfolioapp.prize.PrizeListActivity
import com.example.portfolioapp.prize.PrizeManager
import com.example.portfolioapp.prize.WritePrizeActivity

class CertificateViewActivity : AppCompatActivity() {

    lateinit var certificate: CertificateManager
    lateinit var prize: PrizeManager

    lateinit var certificatesqlitedb: SQLiteDatabase        //certificatedb연동
    lateinit var prizesqlitedb: SQLiteDatabase              //prizedb연동

    lateinit var certificatelayout: LinearLayout            //certificate layout
    lateinit var prizelayout: LinearLayout                  //prize layout

    lateinit var nav_portfolio: ImageView
    lateinit var nav_home: ImageView
    lateinit var nav_certificate: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificate_view)

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        //페이지 정보 제공
        supportActionBar?.setTitle("자격증/수상경력")

        //밑의 하단바 이미지 뷰 클릭했을때 동작
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


        //certificate table 보여주기
        certificate = CertificateManager(this, "certificate", null, 1)      //CertificateManager의 table 가져오기
        certificatesqlitedb = certificate.readableDatabase                                   //certificate table 읽어오기

        certificatelayout = findViewById(R.id.certificate)



        var certificateCursor: Cursor               //certificateCursor 연결
        certificateCursor = certificatesqlitedb.rawQuery("SELECT * FROM certificate;", null)        //certificate의 모든 내용 가져오기

        var certificatenum: Int = 0                 //certificatnum을 통해 이동을 구현

        while (certificateCursor.moveToNext()) {        //cursor가 움직인다면
            var str_name =
                    certificateCursor.getString(certificateCursor.getColumnIndex("name")).toString()


            //layout_item 을 구현(수직으로 보이게)
            var layout_item: LinearLayout = LinearLayout(this)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id = certificatenum

            //우선 자격증명만 보여주게함
            var tvName: TextView = TextView(this)
            tvName.text = str_name
            tvName.textSize = 30f
            layout_item.addView(tvName)


            //자격증명을 클릭하면 상세화면으로 넘어가기(상세화면에서 취득날짜,유효기간,비고,url 등을 확인할 수 있음)
            layout_item.setOnClickListener {
                val intent = Intent(this, CertificateListActivity::class.java)
                intent.putExtra("intent_name", str_name)
                startActivity(intent)
            }

            certificatelayout.addView(layout_item)
            certificatenum++
        }
        //db연동 종료
        certificateCursor.close()
        certificatesqlitedb.close()
        certificate.close()


        //prize table 보여주기
        prize = PrizeManager(this, "prize", null, 1)            //PrizeManager의 table 가져오기
        prizesqlitedb = prize.readableDatabase                                              //prize table 읽어오기

        prizelayout = findViewById(R.id.prize)

        var prizecursor: Cursor
        prizecursor = prizesqlitedb.rawQuery("SELECT * FROM prize;", null)      //prize의 모든 내용 가져오기

        var prizenum: Int = 0

        while (prizecursor.moveToNext()) {                                                       //cursor가 움직인다면
            var str_contestname = prizecursor.getString(prizecursor.getColumnIndex("name")).toString()

            //layout_item 을 구현(수직으로 보이게)
            var prize_layout_item: LinearLayout = LinearLayout(this)
            prize_layout_item.orientation = LinearLayout.VERTICAL
            prize_layout_item.id = prizenum

            //우선 수상경력 이름만 보여주고
            var tvContestName: TextView = TextView(this)
            tvContestName.text = str_contestname
            tvContestName.textSize = 30f
            prize_layout_item.addView(tvContestName)

            //수상경력 이름을 클릭하면 상세화면으로 넘어가기
            prize_layout_item.setOnClickListener {
                val intent = Intent(this, PrizeListActivity::class.java)
                intent.putExtra("intent_name", str_contestname)
                startActivity(intent)
            }

            prizelayout.addView(prize_layout_item)
            prizenum++
        }

        prizecursor.close()
        prizesqlitedb.close()
        prize.close()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_certificate_view,menu)
        return true
    }

    //액션바에 뒤로가기 및 자격증,수상경력추가하기 액션 구현
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_write_certificate -> {
                val intent = Intent(this,WriteCertificateActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_write_prize-> {
                val intent = Intent(this,WritePrizeActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
