package com.example.portfolioapp.portfolio

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.example.portfolioapp.R
import com.example.portfolioapp.certprizeFullView.CertificateViewActivity
import com.example.portfolioapp.home.HomeActivity
import com.example.portfolioapp.portFullView.PortfolioFullViewActivity

class PortfolioViewActivity : AppCompatActivity(){

    //DB관련
    lateinit var portfolio: PorflioManager
    lateinit var sqlitedb: SQLiteDatabase

    //DB로 부터 내용 받아올 변수
    lateinit var tv_viewP_name : TextView
    lateinit var tv_viewP_strat : TextView
    lateinit var tv_viewP_end : TextView
    lateinit var tv_viewP_sort : TextView
    lateinit var tv_writeP_content : TextView
    lateinit var tv_viewP_url : TextView

    //네비게이션바
    lateinit var nav_portfolio: ImageView
    lateinit var nav_home: ImageView
    lateinit var nav_certificate: ImageView

    lateinit var str_actName : String
    lateinit var str_actDay_Start : String
    lateinit var str_actDay_End : String
    lateinit var str_actSort : String
    lateinit var str_actContent : String
    lateinit var str_image : String
    lateinit var str_url : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio_view)

        //네비게이션바
        nav_portfolio = findViewById(R.id.nav_portfolio)
        nav_home = findViewById(R.id.nav_home)
        nav_certificate = findViewById(R.id.nav_certificate)

        //위젯과 연결
        tv_viewP_name = findViewById(R.id.tv_viewP_name)
        tv_viewP_strat = findViewById(R.id.tv_viewP_strat)
        tv_viewP_end = findViewById(R.id.tv_viewP_end)
        tv_viewP_sort = findViewById(R.id.tv_viewP_sort)
        tv_writeP_content = findViewById(R.id.tv_writeP_content)

        tv_viewP_url = findViewById(R.id.tv_viewP_url)


        // 아래 버튼 3개
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

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        val intent= intent
        str_actName = intent.getStringExtra("intent_name")!!  //작성완료 버튼클릭시 해당 페이지 이동 + intent_name을 전달받음
                                                                    //작성한 활동명을 str_actName에 대입

        supportActionBar?.setTitle(str_actName)  //사용자가 작성한 활동명이 타이틀로 나타납니다

        //DB
        portfolio = PorflioManager(this,"portfolio",null,1)
        sqlitedb = portfolio.readableDatabase


        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM portfolio WHERE name = '"+str_actName+"';",null)

        //초기화
        str_actDay_Start=""
        str_actDay_End=""
        str_actSort=""
        str_actContent=""
        str_url=""

        //해당 선택한 활동 내용 읽어오기
        cursor.moveToFirst()  //cursor를 처음으로 이동
        str_actDay_Start = cursor.getString(cursor.getColumnIndex("startDate")).toString()
        str_actDay_End = cursor.getString(cursor.getColumnIndex("EndDate")).toString()
        str_actSort = cursor.getString(cursor.getColumnIndex("sort")).toString()
        str_actContent = cursor.getString(cursor.getColumnIndex("content")).toString()
        str_image = cursor.getString(cursor.getColumnIndex("image")).toString()
        str_url = cursor.getString(cursor.getColumnIndex("url")).toString()

        Log.d("myDB", "str_url : " + str_url)

        cursor.close()
        sqlitedb.close()
        portfolio.close()

        tv_viewP_name.text = str_actName
        tv_viewP_strat.text = str_actDay_Start
        tv_viewP_end.text = str_actDay_End
        tv_viewP_sort.text = str_actSort
        tv_writeP_content.text = str_actContent
        tv_viewP_url.text = str_url

        //깃 주소로 가기(인터넷 주소 연결)
        tv_viewP_url.setOnClickListener{

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =Uri.parse("https://github.com/Lee-Yeonghyeon/PortfolioApp/tree/master")
            if(intent.resolveActivity(packageManager) != null){
                startActivity(intent)
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_portfolio_view_modify,menu)
        return true
    }


    //상단의 액션바에서 뒤로가기 및 삭제/수정 구현
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            //수정 버튼 클릭 시
            R.id.action_portfolio_revise -> {
                val intent2 = Intent(this, PortfolioModifyActivity::class.java)     //PortfolioModifyActivity로 넘어가 사용자가 상세내역을 수정하도록 함
                startActivity(intent2)
                return true
            }
            //삭제 버튼 클릭 시
            R.id.action_portfolio_delete ->{
                portfolio = PorflioManager(this, "portfolio", null, 1)
                sqlitedb = portfolio.readableDatabase
                sqlitedb.execSQL( "DELETE FROM portfolio WHERE name = '"+str_actName+"';")

                Toast.makeText(this, "삭제 완료", Toast.LENGTH_SHORT).show()  //토스트 메세지 출력

                sqlitedb.close()
                portfolio.close()

                val intent = Intent(this, PortfolioFullViewActivity::class.java)  //활동별 모아보기로 넘어감
                startActivity(intent)
                return true
            }
            //뒤로가기 버튼 클릭 시
            android.R.id.home ->{
                val intent = Intent(this, PortfolioFullViewActivity::class.java)
                startActivity(intent)
                return true
            }
            else->{
                return super.onOptionsItemSelected(item)
            }
        }
    }


}