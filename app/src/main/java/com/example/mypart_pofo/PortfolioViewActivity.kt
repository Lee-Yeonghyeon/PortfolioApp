package com.example.mypart_pofo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.widget.*
import java.net.URL

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

    lateinit var photouri: String


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


        val intent= intent
        str_actName = intent.getStringExtra("intent_name")!!

        portfolio = PorflioManager(this,"portfolio",null,1)
        sqlitedb = portfolio.readableDatabase

        Log.d("myDB","intent_name: "+str_actName)

        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM portfolio WHERE name = '"+str_actName+"';",null)
        //cursor = sqlitedb.rawQuery("SELECT * FROM portfolio;",null)

        str_actDay_Start=""
        str_actDay_End=""
        str_actSort=""
        str_actContent=""

        //if(cursor.moveToNext()){
            //if(str_actName==cursor.getString(cursor.getColumnIndex("name")).toString()){
                cursor.moveToFirst()
                str_actDay_Start = cursor.getString(cursor.getColumnIndex("startDate")).toString()
                str_actDay_End = cursor.getString(cursor.getColumnIndex("EndDate")).toString()
                str_actSort = cursor.getString(cursor.getColumnIndex("sort")).toString()
                str_actContent = cursor.getString(cursor.getColumnIndex("content")).toString()
                str_image = cursor.getString(cursor.getColumnIndex("image")).toString()

                //str_image = getAllPhotos()

                Log.d("myDB", "str_image : " + str_image)



             //ImgV_viewP_picture.setImageResource(str_image.toInt())

             /*  var image_task: URLtoBitmapTask = URLtoBitmapTask()
                image_task = URLtoBitmapTask().apply { url = URL(str_image) }
                var bitmap: Bitmap = image_task.execute().get()
                ImgV_viewP_picture.setImageBitmap(bitmap)*/




        //}
//            str_actDay_Start = cursor.getString(cursor.getColumnIndex("startDate")).toString()
//            str_actDay_End = cursor.getString(cursor.getColumnIndex("EndDate")).toString()
//            str_actSort = cursor.getString(cursor.getColumnIndex("sort")).toString()
//            str_actContent = cursor.getString(cursor.getColumnIndex("content")).toString()
//            //str_image = cursor.getString(cursor.getColumnIndex("image")).toString()
//            Log.d("myDB","str_actContent: "+str_actContent)
//            var image_task: URLtoBitmapTask = URLtoBitmapTask()
//            image_task = URLtoBitmapTask().apply { url = URL("{이미지의 url}") }
//            var bitmap: Bitmap = image_task.execute().get()
//            ImgV_viewP_picture.setImageBitmap(bitmap)
        //}

        cursor.close()
        sqlitedb.close()
        portfolio.close()


        tv_viewP_name.text = str_actName
        tv_viewP_strat.text = str_actDay_Start
        tv_viewP_end.text = str_actDay_End
        tv_viewP_sort.text = str_actSort
        tv_writeP_content.text = str_actContent


        //수정하기 버튼을 눌렀을때
        btn_viewP_modify.setOnClickListener {

            var activity_name: String = tv_viewP_name.text.toString()

            val intent = Intent(this, PortfolioModifyActivity::class.java)
            intent.putExtra("intent_name", activity_name)
            startActivity(intent)

        }


        //삭제하기 버튼을 눌렀을때
        btn_viewP_delete.setOnClickListener {
            portfolio = PorflioManager(this, "portfolio", null, 1)
            sqlitedb = portfolio.readableDatabase
            sqlitedb.execSQL( "DELETE FROM portfolio WHERE name = '"+str_actName+"';")

            Toast.makeText(this, "삭제 완료", Toast.LENGTH_SHORT).show()

            sqlitedb.close()
            portfolio.close()
        }

    }

    /*
    private fun getAllPhotos(): String {

        var cursor1 : Cursor

        portfolio = PorflioManager(this, "portfolio", null, 1)
        sqlitedb = portfolio.readableDatabase
        cursor1= sqlitedb.rawQuery( "SELECT FROM portfolio WHERE image = '"+str_actName+"';",null)

        while (cursor1.moveToNext()) {

            var imageurl = cursor1.getString(cursor1.getColumnIndex("image")).toString()
            photouri = imageurl
            Log.d("myDB","photouri: " + photouri)

        }

        return photouri

        cursor1.close()
        sqlitedb.close()
        portfolio.close()

    }*/

}