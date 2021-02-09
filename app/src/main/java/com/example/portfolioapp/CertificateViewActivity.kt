package com.example.portfolioapp

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView

class CertificateViewActivity : AppCompatActivity() {

    lateinit var certificate: CertificateManager
    //lateinit var prize: PrizeManager

    lateinit var certificatesqlitedb: SQLiteDatabase        //certificatedb연동
    lateinit var prizesqlitedb: SQLiteDatabase              //certificatedb연동

    lateinit var layout: LinearLayout                       //certificate layout
    lateinit var prizelayout: LinearLayout                  //prize layout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificate_view)

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        supportActionBar?.setTitle("자격증/수상경력")

        //certificate 보여주기
        certificate = CertificateManager(this,"certificate",null,1)
        certificatesqlitedb = certificate.readableDatabase

        layout = findViewById(R.id.certificate)

        var certificateCursor:Cursor
        certificateCursor = certificatesqlitedb.rawQuery("SELECT * FROM certificate;",null)

        var certificatenum:Int=0

        while(certificateCursor.moveToNext()){
            var str_name = certificateCursor.getString(certificateCursor.getColumnIndex("name")).toString()
            var str_date = certificateCursor.getString(certificateCursor.getColumnIndex("date")).toString()
            var str_period = certificateCursor.getString(certificateCursor.getColumnIndex("period")).toString()
            var str_etc = certificateCursor.getString(certificateCursor.getColumnIndex("etc")).toString()

            var layout_item: LinearLayout = LinearLayout(this)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id=certificatenum

            //우선 제목만 보여주고
            var tvName: TextView = TextView(this)
            tvName.text = str_name
            tvName.textSize = 30f
            layout_item.addView(tvName)


            //제목 클릭하면 상세화면으로 넘어가기
            layout_item.setOnClickListener{
                val intent = Intent(this,CertificateListActivity::class.java)
                intent.putExtra("intent_name",str_name)
                startActivity(intent)
            }

            layout.addView(layout_item)
            certificatenum++
        }

        certificateCursor.close()
        certificatesqlitedb.close()
        certificate.close()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_certificate_view,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_write_certificate -> {
                val intent = Intent(this,WriteCertificateActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_write_prize-> {
                val intent = Intent(this,WritePrizeActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
