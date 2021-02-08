package com.example.mypart_pofo

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

class PortfolioViewActivity : AppCompatActivity(){

    lateinit var sqlitedb : SQLiteDatabase

    lateinit var tvActivity_Name : TextView
    lateinit var tvActivity_Day_Start : TextView
    lateinit var tvActivity_Day_End : TextView
    lateinit var tvActivity_sort : Spinner
    lateinit var tvActivity_content : TextView

    lateinit var str_actName : String
    lateinit var str_actDay_Start : String
    lateinit var str_actDay_End : String
    lateinit var str_actSort : String
    lateinit var str_actContent : String

    lateinit var btn_writeP_modify : Button
    lateinit var btn_writeP_delete : Button

    private var portfolioDB : PorflioManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio_view)

        supportActionBar?.setTitle("ㅇㅇ 공모전") //사용자가 선택한 공모전 이름이 타이틀로 바뀔 수 있게 수정해주세요

        tvActivity_Name = findViewById(R.id.edt_writeP_name)
        tvActivity_Day_Start = findViewById(R.id.calendarTextViewStart)
        tvActivity_Day_End = findViewById(R.id.calendarTextViewEnd)
        tvActivity_sort = findViewById(R.id.spinner_writeP_sort)
        tvActivity_content = findViewById(R.id.edt_writeP_content)


        btn_writeP_modify = findViewById(R.id.btn_writeP_modify)
        btn_writeP_delete = findViewById(R.id.btn_writeP_delete)

        val intent= intent
        str_actName = intent.getStringExtra("intent_actName").toString()

        portfolioDB = PorflioManager(this,"personnelDB",null,1)
        sqlitedb = portfolioDB!!.readableDatabase

        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM personnel WHERE name = '"+str_actName+"';",null)

        if(cursor.moveToNext()){
            str_actDay_Start = cursor.getString(cursor.getColumnIndex(""))
            str_actDay_End = cursor.getString(cursor.getColumnIndex(""))
            str_actSort = cursor.getString(cursor.getColumnIndex(""))
            str_actContent = cursor.getString(cursor.getColumnIndex(""))

        }


    }



}