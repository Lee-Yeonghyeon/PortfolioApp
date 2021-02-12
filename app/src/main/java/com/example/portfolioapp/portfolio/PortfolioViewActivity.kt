package com.example.portfolioapp.portfolio
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import com.example.portfolioapp.R
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
    lateinit var ImgV_viewP_picture : ImageView
    lateinit var tv_viewP_url : TextView

    lateinit var btn_viewP_modify : Button
    lateinit var btn_viewP_delete : Button

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
    lateinit var photourl: String

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
        ImgV_viewP_picture = findViewById(R.id.ImgV_viewP_picture)
        tv_viewP_url = findViewById(R.id.tv_viewP_url)

        btn_viewP_modify = findViewById(R.id.btn_viewP_modify)
        btn_viewP_delete = findViewById(R.id.btn_viewP_delete)

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

        //해당 선택한 활동 내용 읽어오기
        cursor.moveToFirst()  //cursor를 처음으로 이동
        str_actDay_Start = cursor.getString(cursor.getColumnIndex("startDate")).toString()
        str_actDay_End = cursor.getString(cursor.getColumnIndex("EndDate")).toString()
        str_actSort = cursor.getString(cursor.getColumnIndex("sort")).toString()
        str_actContent = cursor.getString(cursor.getColumnIndex("content")).toString()
        str_image = cursor.getString(cursor.getColumnIndex("image")).toString()
        str_url = cursor.getString(cursor.getColumnIndex("url")).toString()

        Log.d("myDB", "str_image : " + str_image)

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


        //이미지 출력
        //ImgV_viewP_picture.setImageResource(str_image.toInt())

        /*  var image_task: URLtoBitmapTask = URLtoBitmapTask()
            image_task = URLtoBitmapTask().apply { url = URL(str_image) }
            var bitmap: Bitmap = image_task.execute().get()
            ImgV_viewP_picture.setImageBitmap(bitmap)*/



        //수정하기 버튼을 눌렀을때
        btn_viewP_modify.setOnClickListener {

            //수정하는 페이지로 이동
            val intent2 = Intent(this, PortfolioModifyActivity::class.java)
            startActivity(intent2)

        }


        //삭제하기 버튼을 눌렀을때_DB에서 데이터 삭제
        btn_viewP_delete.setOnClickListener {
            portfolio = PorflioManager(this, "portfolio", null, 1)
            sqlitedb = portfolio.readableDatabase
            sqlitedb.execSQL( "DELETE FROM portfolio WHERE name = '"+str_actName+"';")

            Toast.makeText(this, "삭제 완료", Toast.LENGTH_SHORT).show()  //토스트 메세지 출력

            sqlitedb.close()
            portfolio.close()

            val intent = Intent(this, PortfolioFullViewActivity::class.java)  //활동별 모아보기로 넘어감
            startActivity(intent)
        }

    }

    //뒤로가기 버튼작동
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
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