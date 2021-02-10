package com.example.portfolioapp

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class WritePrizeActivity : AppCompatActivity() {

    lateinit var prize: PrizeManager
    lateinit var prizesqlitedb: SQLiteDatabase

    lateinit var edt_writeP_contestName: EditText
    lateinit var edt_writeP_prizeName: EditText
    lateinit var btn_writeP_date: Button
    lateinit var edt_writeP_date: TextView
    lateinit var edt_writeP_contents: EditText
    lateinit var edt_writeP_etc: EditText
    lateinit var btn_writeP_picture: Button
    lateinit var prizeimg: ImageView
    lateinit var btn_writeP_file: Button
    lateinit var btn_writeP_complete: Button

    lateinit var nav_portfolio: ImageView
    lateinit var nav_home: ImageView
    lateinit var nav_certificate: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_prize)

        edt_writeP_contestName = findViewById(R.id.edt_writeP_contestName)
        edt_writeP_prizeName = findViewById(R.id.edt_writeP_prizeName)
        btn_writeP_date = findViewById(R.id.btn_writeP_date)
        edt_writeP_date = findViewById(R.id.edt_writeP_date)
        edt_writeP_contents = findViewById(R.id.edt_writeP_contents)
        edt_writeP_etc = findViewById(R.id.edt_writeP_etc)
        btn_writeP_picture = findViewById(R.id.btn_writeP_picture)
        prizeimg = findViewById(R.id.prizeimg)
        btn_writeP_file = findViewById(R.id.btn_writeP_file)
        btn_writeP_complete = findViewById(R.id.btn_writeP_complete)

        nav_portfolio = findViewById(R.id.nav_portfolio)
        nav_home = findViewById(R.id.nav_home)
        nav_certificate = findViewById(R.id.nav_certificate)

        supportActionBar?.setTitle("수상내역 입력하기")

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

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        //날짜선택 클릭했을때
        btn_writeP_date.setOnClickListener{
            var today = Calendar.getInstance()
            var year: Int = today.get(Calendar.YEAR)
            var month: Int = today.get(Calendar.MONTH)
            var date: Int = today.get(Calendar.DATE)

            var listener = DatePickerDialog.OnDateSetListener{ datePicker, year, month, date ->
                edt_writeP_date.text = "${year}년 ${month + 1}월 ${date}일"
            }
            var picker = DatePickerDialog(this,listener,year,month,date)
            picker.show()
        }
        //DB연동
        prize = PrizeManager(this,"prize",null,1)

        //사진첨부 클릭했을때
        btn_writeP_picture.setOnClickListener{
            openGallery()               //openGallery함수 호출
        }
        //작성완료 클릭했을때
        btn_writeP_complete.setOnClickListener{
            var str_contestname:String=edt_writeP_contestName.text.toString()
            var str_prizename:String=edt_writeP_prizeName.text.toString()
            var str_date:String=" "
            var str_contents:String=edt_writeP_contents.text.toString()
            var str_etc:String=edt_writeP_etc.text.toString()

            if(edt_writeP_date.text !== null){
                str_date = edt_writeP_date.text.toString()
            }

            prizesqlitedb = prize.writableDatabase
            prizesqlitedb.execSQL("INSERT INTO prize VALUES ('"+str_contestname+"','"+str_prizename+"','"+str_date+"','"+str_contents+"','"+str_etc+"');")
            prizesqlitedb.close()

            val intent = Intent(this,CertificateViewActivity::class.java)
            intent.putExtra("intent_name",str_contestname)
            startActivity(intent)
        }

    }
    private val OPEN_GALLERY = 1

    private fun openGallery(){
        val intent:Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent,OPEN_GALLERY)
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK){
            if(requestCode==OPEN_GALLERY){
                var currentImageUrl : Uri? = data?.data

                try {
                    val bitmap= MediaStore.Images.Media.getBitmap(contentResolver,currentImageUrl)
                    prizeimg.setImageBitmap(bitmap)
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }else{
            Log.d("ActivityResult","sth wrong")
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home ->{
                val intent = Intent(this,CertificateViewActivity::class.java)
                startActivity(intent)
                return true
            }
            else->{
                return super.onOptionsItemSelected(item)
            }
        }
    }

}