package com.example.portfolioapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.example.mypart_pofo.PorflioManager

class PortfolioViewActivity : AppCompatActivity(){


    //DB관련
    lateinit var portfolio: PorflioManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var tv_viewP_name : TextView
    lateinit var tv_viewP_strat : TextView
    lateinit var tv_viewP_end : TextView
    lateinit var tv_viewP_sort : TextView
    lateinit var tv_writeP_content : TextView

    lateinit var ImgV_viewP_picture : ImageView
    lateinit var imageUri : Uri

    lateinit var btn_viewP_modify : Button
    lateinit var btn_viewP_delete : Button

    lateinit var str_actName : String
    lateinit var str_actDay_Start : String
    lateinit var str_actDay_End : String
    lateinit var str_actSort : String
    lateinit var str_actContent : String
    lateinit var str_image : String

    lateinit var nav_portfolio: ImageView
    lateinit var nav_home: ImageView
    lateinit var nav_certificate: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio_view)

        supportActionBar?.setTitle("ㅇㅇ 공모전") //사용자가 선택한 공모전 이름이 타이틀로 바뀔 수 있게 수정해주세요

        //위젯과 연결
        tv_viewP_name = findViewById(R.id.tv_viewP_name)
        tv_viewP_strat = findViewById(R.id.tv_viewP_strat)
        tv_viewP_end = findViewById(R.id.tv_viewP_end)
        tv_viewP_sort = findViewById(R.id.tv_viewP_sort)
        tv_writeP_content = findViewById(R.id.tv_writeP_content)
        ImgV_viewP_picture = findViewById(R.id.ImgV_viewP_picture)


        btn_viewP_modify = findViewById(R.id.btn_viewP_modify)
        btn_viewP_delete = findViewById(R.id.btn_viewP_delete)

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


        val intent= intent
        str_actName = intent.getStringExtra("intent_actName").toString()

        portfolio = PorflioManager(this,"portfolio",null,1)
        sqlitedb = portfolio.readableDatabase

        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM portfolio WHERE name = '"+str_actName+"';",null)

        if(cursor.moveToNext()){
            str_actDay_Start = cursor.getString(cursor.getColumnIndex("startDate")).toString()
            str_actDay_End = cursor.getString(cursor.getColumnIndex("EndDate")).toString()
            str_actSort = cursor.getString(cursor.getColumnIndex("sort")).toString()
            str_actContent = cursor.getString(cursor.getColumnIndex("content")).toString()
            str_image = cursor.getString(cursor.getColumnIndex("image")).toString()

        }

        cursor.close()
        sqlitedb.close()
        portfolio.close()


        tv_viewP_name.text = str_actName
        tv_viewP_strat.text = str_actDay_Start
        tv_viewP_end.text = str_actDay_End
        tv_viewP_sort.text = str_actSort
        tv_writeP_content.text = str_actContent

        btn_viewP_delete.setOnClickListener {
            portfolio = PorflioManager(this, "portfolio", null, 1)
            sqlitedb = portfolio.readableDatabase
            sqlitedb.execSQL( "DELETE FROM portfolio WHERE name = '"+str_actName+"';")

            sqlitedb.close()
            portfolio.close()
        }




        //ImgV_viewP_picture.setImageResource(imageUri.toString())
    }






}