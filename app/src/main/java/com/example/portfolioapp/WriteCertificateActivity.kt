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

class WriteCertificateActivity : AppCompatActivity() {

    lateinit var certificate:CertificateManager
    lateinit var certificatesqlitedb: SQLiteDatabase

    lateinit var edt_writeC_name: EditText
    lateinit var btn_writeC_selectDate: Button
    lateinit var edt_writeC_date: TextView
    lateinit var edt_writeC_selectPeriod: EditText
    lateinit var edt_writeC_etc: EditText
    lateinit var btn_writeC_picture: Button
    lateinit var certificateimg: ImageView
    lateinit var edt_writeC_url: EditText
    lateinit var btn_writeC_complete: Button

    lateinit var nav_portfolio: ImageView
    lateinit var nav_home: ImageView
    lateinit var nav_certificate: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_certificate)

        edt_writeC_name = findViewById(R.id.edt_writeC_name)
        btn_writeC_selectDate = findViewById(R.id.btn_writeC_selectDate)
        edt_writeC_date = findViewById(R.id.edt_writeC_date)
        edt_writeC_selectPeriod = findViewById(R.id.edt_writeC_selectPeriod)
        edt_writeC_etc = findViewById(R.id.edt_writeC_etc)
        btn_writeC_picture = findViewById(R.id.btn_writeC_picture)
        certificateimg = findViewById(R.id.certificateimg)
        edt_writeC_url = findViewById(R.id.edt_writeC_url)
        btn_writeC_complete = findViewById(R.id.btn_writeC_complete)

        nav_portfolio = findViewById(R.id.nav_portfolio)
        nav_home = findViewById(R.id.nav_home)
        nav_certificate = findViewById(R.id.nav_certificate)

        supportActionBar?.setTitle("자격증 입력하기")

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

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

        //날짜선택 클릭했을때
        btn_writeC_selectDate.setOnClickListener{
            var today = Calendar.getInstance()
            var year: Int = today.get(Calendar.YEAR)
            var month: Int = today.get(Calendar.MONTH)
            var date: Int = today.get(Calendar.DATE)

            var listener = DatePickerDialog.OnDateSetListener{ datePicker, year, month, date ->
                edt_writeC_date.text = "${year}년 ${month + 1}월 ${date}일"
            }
            var picker = DatePickerDialog(this,listener,year,month,date)
            picker.show()
        }
        //DB 연동
        certificate= CertificateManager(this,"certificate",null,1)

        //사진첨부 클릭했을때
        btn_writeC_picture.setOnClickListener{
            openGallery()               //openGallery함수 호출
        }
        //작성완료 클릭했을때
        btn_writeC_complete.setOnClickListener{
            var str_name: String = edt_writeC_name.text.toString()
            var str_date: String =" "
            var str_period: String = edt_writeC_selectPeriod.text.toString()
            var str_etc: String = edt_writeC_etc.text.toString()
            var str_url: String = edt_writeC_url.text.toString()

            if(edt_writeC_date.text !==null){
                str_date = edt_writeC_date.text.toString()
            }

            certificatesqlitedb = certificate.writableDatabase
            certificatesqlitedb.execSQL("INSERT INTO certificate VALUES ('" + str_name +"','"
                    + str_date + "',"+"'"+str_period+"'"+",'"+str_etc+"'"+",'"+str_url+"');")
            certificatesqlitedb.close()

            val intent = Intent(this,CertificateViewActivity::class.java)
            intent.putExtra("intent_name",str_name)
            startActivity(intent)
        }

    }
    private val OPEN_GALLERY = 1

    private fun openGallery(){
        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("images/*")
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
                    certificateimg.setImageBitmap(bitmap)
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