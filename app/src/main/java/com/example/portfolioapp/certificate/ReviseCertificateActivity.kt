package com.example.portfolioapp.certificate

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.Intent.*
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.*
import com.example.portfolioapp.certprizeFullView.CertificateViewActivity
import com.example.portfolioapp.R
import com.example.portfolioapp.portCalendar.PortfolioCalendarViewActivity
import com.example.portfolioapp.home.HomeActivity
import com.example.portfolioapp.portFullView.PortfolioFullViewActivity
import java.util.*

class ReviseCertificateActivity : AppCompatActivity() {

    //관련 변수 선언
    lateinit var certificate: CertificateManager
    lateinit var certificatesqlitedb: SQLiteDatabase

    //사용자가 직접 작성하게될 내용의 변수와 관련 버튼
    lateinit var edt_writeC_name: EditText
    lateinit var btn_writeC_selectDate: Button
    lateinit var edt_writeC_date: TextView
    lateinit var edt_writeC_selectPeriod: EditText
    lateinit var edt_writeC_etc: EditText
    lateinit var btn_writeC_picture: Button
    lateinit var certificateimg: ImageView
    lateinit var edt_writeC_url: EditText
    lateinit var btn_writeC_revise: Button

    lateinit var nav_portfolio: ImageView
    lateinit var nav_home: ImageView
    lateinit var nav_certificate: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revise_certificate)

        //xml과 연결
        edt_writeC_name = findViewById(R.id.edt_writeC_name)
        btn_writeC_selectDate = findViewById(R.id.btn_writeC_selectDate)
        edt_writeC_date = findViewById(R.id.edt_writeC_date)
        edt_writeC_selectPeriod = findViewById(R.id.edt_writeC_selectPeriod)
        edt_writeC_etc = findViewById(R.id.edt_writeC_etc)
        btn_writeC_picture = findViewById(R.id.btn_writeC_picture)
        certificateimg = findViewById(R.id.certificateimg)
        edt_writeC_url = findViewById(R.id.edt_writeC_url)
        btn_writeC_revise = findViewById(R.id.btn_writeC_revise)

        nav_portfolio = findViewById(R.id.nav_portfolio)
        nav_home = findViewById(R.id.nav_home)
        nav_certificate = findViewById(R.id.nav_certificate)

        supportActionBar?.setTitle("자격증 수정하기")

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        //밑의 하단바 이미지 뷰 클릭했을때 동작
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

        //날짜선택 클릭했을때
        btn_writeC_selectDate.setOnClickListener {
            var today = Calendar.getInstance()
            var year: Int = today.get(Calendar.YEAR)
            var month: Int = today.get(Calendar.MONTH)
            var date: Int = today.get(Calendar.DATE)

            var listener = DatePickerDialog.OnDateSetListener { datePicker, year, month, date ->
                edt_writeC_date.text = "${year}년 ${month + 1}월 ${date}일"
            }
            var picker = DatePickerDialog(this, listener, year, month, date)
            picker.show()
        }
        //DB 연동
        certificate = CertificateManager(this, "certificate", null, 1)

        //사진첨부 클릭했을때
        btn_writeC_picture.setOnClickListener {
            openGallery()               //openGallery함수 호출
        }
        //수정완료 클릭했을때
        btn_writeC_revise.setOnClickListener {
            var str_name: String = edt_writeC_name.text.toString()
            //날짜는 우선 Sring으로 선언
            var str_date: String = " "
            var str_period: String = edt_writeC_selectPeriod.text.toString()
            var str_etc: String = edt_writeC_etc.text.toString()
            var str_url: String = edt_writeC_url.text.toString()

            //날짜가 null이 아니라면 str_date값을 가져오기
            if (edt_writeC_date.text !== null) {
                str_date = edt_writeC_date.text.toString()
            }

            //certificate에 작성
            certificatesqlitedb = certificate.writableDatabase

            //certificate table에 sql update문을 통해  사용자가 수정한 값 작성
            certificatesqlitedb.execSQL(
                    "UPDATE certificate SET date='" + str_date + "', period='"
                            + str_period + "',etc='" + str_etc+"', url='"+str_url
                            + "' WHERE name = '" + str_name + "';"
            )
            certificatesqlitedb.close()

            Toast.makeText(this,"수정 완료", Toast.LENGTH_SHORT).show()

            //수정한 자격증 내역을 CertificateViewActivity에서 확인할 수 있음
            val intent = Intent(this, CertificateViewActivity::class.java)
            intent.putExtra("intent_name", str_name)
            startActivity(intent)

        }
    }
    //opeGallery함수 호출
    private val OPEN_GALLERY = 1

    private fun openGallery(){
        val intent: Intent = Intent(ACTION_GET_CONTENT)             //내용물을 받아오고
        intent.setType("image/*")                                   //sdcard의 images를 받아옴
        startActivityForResult(intent,OPEN_GALLERY)                 //갤러리를 열기
    }

    //갤러리에 연동해서 Uri가져오기 -> 작성한 페이지에 사진이 보여짐
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

    //액션바
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