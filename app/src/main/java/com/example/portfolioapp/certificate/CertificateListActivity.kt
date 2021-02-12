package com.example.portfolioapp.certificate

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

class CertificateListActivity : AppCompatActivity() {

    //관련 변수 선언
    lateinit var certificate: CertificateManager
    lateinit var sqlitedb: SQLiteDatabase

    //사용자가 입력한 값을 넘겨받는 변수
    lateinit var tvCertificateName: TextView
    lateinit var tvCertificateDate: TextView
    lateinit var tvCertificatePeriod: TextView
    lateinit var tvCertificateEtc: TextView
    lateinit var tvCertificateUrl: TextView

    lateinit var str_certificatename: String
    lateinit var str_certificatedate: String
    lateinit var str_certificateperiod: String
    lateinit var str_certificateetc: String
    lateinit var str_certificateurl: String

    lateinit var nav_portfolio: ImageView
    lateinit var nav_home: ImageView
    lateinit var nav_certificate: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificate_list)

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        //xml과 연결
        tvCertificateName = findViewById(R.id.certificatename)
        tvCertificateDate = findViewById(R.id.certificatedate)
        tvCertificatePeriod = findViewById(R.id.certificateperiod)
        tvCertificateEtc = findViewById(R.id.certificateetc)
        tvCertificateUrl = findViewById(R.id.certificateurl)


        //밑의 하단바 이미지 뷰 클릭했을때 동작
        nav_portfolio = findViewById(R.id.nav_portfolio)
        nav_home = findViewById(R.id.nav_home)
        nav_certificate = findViewById(R.id.nav_certificate)

        nav_portfolio.setOnClickListener {
            val intent = Intent(this, PortfolioCalendarViewActivity::class.java)
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

        //클릭한 자격증 이름으로 넘겨받기
        val intent = intent
        str_certificatename = intent.getStringExtra("intent_name").toString()

        //DB연동
        certificate = CertificateManager(this, "certificate", null, 1)
        sqlitedb = certificate.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM certificate WHERE name = '"+str_certificatename+"';", null)

        //커서가 이동하며 날짜,기간,기타,url을 보이도록 함
        if (cursor.moveToNext()) {
            str_certificatedate = cursor.getString((cursor.getColumnIndex("date"))).toString()
            str_certificateperiod = cursor.getString(cursor.getColumnIndex("period")).toString()
            str_certificateetc = cursor.getString((cursor.getColumnIndex("etc"))).toString()
            str_certificateurl = cursor.getString((cursor.getColumnIndex("url"))).toString()
        }

        //DB연동 종료
        cursor.close()
        sqlitedb.close()
        certificate.close()

        //받은 str_ 값들을 tv값에 넘겨주어 text를 볼 수 있게함
        tvCertificateName.text = str_certificatename
        tvCertificateDate.text = str_certificatedate
        tvCertificatePeriod.text = str_certificateperiod
        tvCertificateEtc.text = str_certificateetc
        tvCertificateUrl.text = str_certificateurl+"\n"

        //클릭한 자격증 이름으로 액션바 타이틀 변경
        supportActionBar?.setTitle(tvCertificateName.text)

        //깃 주소로 가기(인터넷 주소 연결)
        tvCertificateUrl.setOnClickListener{

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =Uri.parse("https://www.toeic.co.kr/")
            //intent.data = Uri.parse(tvCertificateGit.text.toString())
            if(intent.resolveActivity(packageManager) != null){
                startActivity(intent)
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_certificate_list, menu)
        return true
    }

    //상단의 액션바에서 뒤로가기 및 삭제/수정 구현
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            //삭제버튼 클릭시
            R.id.action_certificate_delete -> {
                certificate = CertificateManager(this, "certificate", null, 1)
                sqlitedb = certificate.readableDatabase

                sqlitedb.execSQL("DELETE FROM certificate WHERE name='" + str_certificatename + "';")
                sqlitedb.close()
                certificate.close()

                val intent = Intent(this, CertificateViewActivity::class.java)
                startActivity(intent)
                return true
            }
            //수정버튼 클릭시
            R.id.action_certificate_revise ->{

                val intent = Intent(this, ReviseCertificateActivity::class.java)
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