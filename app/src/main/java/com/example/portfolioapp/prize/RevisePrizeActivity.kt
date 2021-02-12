package com.example.portfolioapp.prize

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
import android.widget.*
import com.example.portfolioapp.certprizeFullView.CertificateViewActivity
import com.example.portfolioapp.R
import com.example.portfolioapp.certificate.CertificateManager
import com.example.portfolioapp.portCalendar.PortfolioCalendarViewActivity
import com.example.portfolioapp.home.HomeActivity
import java.util.*

class RevisePrizeActivity : AppCompatActivity() {

    //관련 변수 선언
    lateinit var prize: PrizeManager
    lateinit var prizesqlitedb: SQLiteDatabase

    //사용자가 직접 작성하게될 내용의 변수와 관련 버튼
    lateinit var edt_writeP_contestName: EditText
    lateinit var edt_writeP_prizeName: EditText
    lateinit var btn_writeP_date: Button
    lateinit var edt_writeP_date: TextView
    lateinit var edt_writeP_contents: EditText
    lateinit var edt_writeP_url: EditText
    lateinit var btn_writeP_picture: Button
    lateinit var prizeimg: ImageView
    lateinit var btn_writeP_revise: Button

    lateinit var nav_portfolio: ImageView
    lateinit var nav_home: ImageView
    lateinit var nav_certificate: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revise_prize)

        //xml과 연결
        edt_writeP_contestName = findViewById(R.id.edt_writeP_contestName)
        edt_writeP_prizeName = findViewById(R.id.edt_writeP_prizeName)
        btn_writeP_date = findViewById(R.id.btn_writeP_date)
        edt_writeP_date = findViewById(R.id.edt_writeP_date)
        edt_writeP_contents = findViewById(R.id.edt_writeP_contents)
        edt_writeP_url = findViewById(R.id.edt_writeP_url)
        btn_writeP_picture = findViewById(R.id.btn_writeP_picture)
        prizeimg = findViewById(R.id.prizeimg)
        btn_writeP_revise = findViewById(R.id.btn_writeP_revise)

        nav_portfolio = findViewById(R.id.nav_portfolio)
        nav_home = findViewById(R.id.nav_home)
        nav_certificate = findViewById(R.id.nav_certificate)

        supportActionBar?.setTitle("수상내역 수정하기")

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        //밑의 하단바 이미지 뷰 클릭했을때 동작
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

        //수정완료 클릭했을때
        btn_writeP_revise.setOnClickListener{
            var str_contestname:String=edt_writeP_contestName.text.toString()
            //날짜는 우선 Sring으로 선언
            var str_prizename:String=edt_writeP_prizeName.text.toString()
            var str_date:String=" "
            var str_contents:String=edt_writeP_contents.text.toString()
            var str_url:String=edt_writeP_url.text.toString()

            //날짜가 null이 아니라면 str_date값을 가져오기
            if(edt_writeP_date.text !==null){
                str_date = edt_writeP_date.text.toString()
            }

            //prize에 작성


            prizesqlitedb = prize.writableDatabase
            //prize table에 sql update문을 통해  사용자가 수정한 값 작성
            prizesqlitedb.execSQL("UPDATE prize SET prizename='"+str_prizename +"',date='" + str_date +"',contents='"
                    +str_contents+"',url='"+str_url
                    +"' WHERE name = '"+str_contestname+"';")
            prizesqlitedb.close()

            Toast.makeText(this,"$str_contestname 수상 경력이 수정되었습니다.", Toast.LENGTH_SHORT).show()

            //수정한 수상 내역을 CertificateViewActivity에서 확인할 수 있음
            val intent = Intent(this,CertificateViewActivity::class.java)
            intent.putExtra("intent_name",str_contestname)
            startActivity(intent)
        }
    }

    //opeGallery함수 호출
    private val OPEN_GALLERY = 1

    private fun openGallery(){
        val intent:Intent = Intent(Intent.ACTION_GET_CONTENT)       //내용물을 받아오고
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
                    prizeimg.setImageBitmap(bitmap)
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