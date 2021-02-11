package com.example.portfolioapp

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

class PrizeListActivity : AppCompatActivity() {

    lateinit var prize: PrizeManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var tvContestName: TextView
    lateinit var tvPrizeName: TextView
    lateinit var tvPrizeDate: TextView
    lateinit var tvPrizeContents: TextView
    lateinit var tvPrizeUrl: TextView

    lateinit var str_contestname: String
    lateinit var str_prizename: String
    lateinit var str_prizedate: String
    lateinit var str_prizecontents: String
    lateinit var str_prizeurl: String

    lateinit var nav_portfolio: ImageView
    lateinit var nav_home: ImageView
    lateinit var nav_certificate: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prize_list)

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        tvContestName = findViewById(R.id.contestname)
        tvPrizeName = findViewById(R.id.prizename)
        tvPrizeDate = findViewById(R.id.prizedate)
        tvPrizeContents = findViewById(R.id.prizecontents)
        tvPrizeUrl = findViewById(R.id.prizeurl)

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


        val intent = intent
        str_contestname = intent.getStringExtra("intent_name").toString()

        prize = PrizeManager(this, "prize", null, 1)
        sqlitedb = prize.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM prize;", null)

        if (cursor.moveToNext()) {
            str_prizename = cursor.getString((cursor.getColumnIndex("prizename"))).toString()
            str_prizedate = cursor.getString((cursor.getColumnIndex("date"))).toString()
            str_prizecontents = cursor.getString(cursor.getColumnIndex("contents")).toString()
            str_prizeurl = cursor.getString((cursor.getColumnIndex("url"))).toString()
        }

        cursor.close()
        sqlitedb.close()
        prize.close()

        tvContestName.text = str_contestname
        tvPrizeName.text = str_prizename
        tvPrizeDate.text = str_prizedate
        tvPrizeContents.text = str_prizecontents
        tvPrizeUrl.text = str_prizeurl

        //깃 주소로 가기(인터넷 주소 연결)
        tvPrizeUrl.setOnClickListener{

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://github.com/Lee-Yeonghyeon/PortfolioApp")
            //intent.data = Uri.parse(tvCertificateGit.text.toString())
            if(intent.resolveActivity(packageManager) != null){
                startActivity(intent)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_prize_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_prize_delete -> {
                prize = PrizeManager(this, "prize", null, 1)
                sqlitedb = prize.readableDatabase

                sqlitedb.execSQL("DELETE FROM prize WHERE name='" + str_contestname + "';")
                sqlitedb.close()
                prize.close()

                val intent = Intent(this, CertificateViewActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_prize_revise ->{
                val intent = Intent(this,RevisePrizeActivity::class.java)
                startActivity(intent)
                return true
            }
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