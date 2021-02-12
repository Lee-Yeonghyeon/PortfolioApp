package com.example.portfolioapp.prize

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.example.portfolioapp.certprizeFullView.CertificateViewActivity
import com.example.portfolioapp.R
import com.example.portfolioapp.portCalendar.PortfolioCalendarViewActivity
import com.example.portfolioapp.home.HomeActivity


class PrizeListActivity : AppCompatActivity() {

    //관련 변수 선언
    lateinit var prize: PrizeManager
    lateinit var sqlitedb: SQLiteDatabase

    //사용자가 입력한 값을 넘겨받는 변수
    lateinit var tvContestName: TextView
    lateinit var tvPrizeName: TextView
    lateinit var tvPrizeDate: TextView
    lateinit var tvPrizeContents: TextView
    lateinit var tvPrizeUrl: TextView

    lateinit var str_contestname: String
    lateinit var str_prizename: String
    lateinit var str_prizedate: String
    lateinit var str_prizecontents: String
    lateinit var str_prizeurl: String

    lateinit var nav_portfolio: ImageView
    lateinit var nav_home: ImageView
    lateinit var nav_certificate: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prize_list)

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        //xml과 연결
        tvContestName = findViewById(R.id.contestname)
        tvPrizeName = findViewById(R.id.prizename)
        tvPrizeDate = findViewById(R.id.prizedate)
        tvPrizeContents = findViewById(R.id.prizecontents)
        tvPrizeUrl = findViewById(R.id.prizeurl)

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

        //클릭한 자격증 이름으로 넘겨받기
        val intent = intent
        str_contestname = intent.getStringExtra("intent_name").toString()

        //DB연동
        prize = PrizeManager(this, "prize", null, 1)
        sqlitedb = prize.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM prize WHERE name = '"+str_contestname+"';", null)            //prize라는 테이블에서 클릭한 name값을 선택하는 sql문

        //커서가 이동하며 날짜,기간,기타,url을 보이도록 함
        if (cursor.moveToNext()) {
            str_prizename = cursor.getString((cursor.getColumnIndex("prizename"))).toString()
            str_prizedate = cursor.getString((cursor.getColumnIndex("date"))).toString()
            str_prizecontents = cursor.getString(cursor.getColumnIndex("contents")).toString()
            str_prizeurl = cursor.getString((cursor.getColumnIndex("url"))).toString()
        }

        //DB연동 종료
        cursor.close()
        sqlitedb.close()
        prize.close()

        //받은 str_ 값들을 tv값에 넘겨주어 text를 볼 수 있게함
        tvContestName.text = str_contestname
        tvPrizeName.text = str_prizename
        tvPrizeDate.text = str_prizedate
        tvPrizeContents.text = str_prizecontents
        tvPrizeUrl.text = str_prizeurl

        //클릭한 수상 이름으로 액션바 타이틀 변경
        supportActionBar?.setTitle(tvContestName.text)

        //깃 주소로 가기(인터넷 주소 연결)
        tvPrizeUrl.setOnClickListener{

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://github.com/Lee-Yeonghyeon/PortfolioApp")
            //intent.data = Uri.parse(tvCertificateGit.text.toString())
            if(intent.resolveActivity(packageManager) != null){
                startActivity(intent)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_prize_list, menu)
        return true
    }

    //상단의 액션바에서 뒤로가기 및 삭제/수정 구현
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            //삭제버튼 클릭시
            R.id.action_prize_delete -> {
                prize = PrizeManager(this, "prize", null, 1)
                sqlitedb = prize.readableDatabase

                sqlitedb.execSQL("DELETE FROM prize WHERE name='" + str_contestname + "';")                 //prize라는 테이블에서 클릭한 name값에 따라 해당 행을 삭제하는 sql문
                sqlitedb.close()
                prize.close()

                val intent = Intent(this, CertificateViewActivity::class.java)                      //삭제하면 CertificateVieActivity로 넘어가서 삭제됨을 바로 확인
                startActivity(intent)
                return true
            }
            //수정버튼 클릭시
            R.id.action_prize_revise ->{
                val intent = Intent(this, RevisePrizeActivity::class.java)                             //RevisePrizeActivity로 넘어가 사용자가 상세내역을 수정하도록 함
                startActivity(intent)
                return true
            }
            //뒤로가기버튼 클릭시
            android.R.id.home -> {
                val intent = Intent(this, CertificateViewActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

}