package com.example.portfolioapp

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView

class CertificateListActivity : AppCompatActivity() {

    lateinit var certificate: CertificateManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var tvCertificateName: TextView
    lateinit var tvCertificateDate: TextView
    lateinit var tvCertificatePeriod: TextView
    lateinit var tvCertificateEtc: TextView

    lateinit var str_certificatename: String
    lateinit var str_certificatedate: String
    lateinit var str_certificateperiod: String
    lateinit var str_certificateetc: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificate_list)

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        tvCertificateName = findViewById(R.id.certificatename)
        tvCertificateDate = findViewById(R.id.certificatedate)
        tvCertificatePeriod = findViewById(R.id.certificateperiod)
        tvCertificateEtc = findViewById(R.id.certificateetc)

        val intent = intent
        str_certificatename = intent.getStringExtra("intent_name").toString()

        certificate = CertificateManager(this, "certificate", null, 1)
        sqlitedb = certificate.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM certificate;", null)

        if (cursor.moveToNext()) {
            str_certificatedate = cursor.getString((cursor.getColumnIndex("date"))).toString()
            str_certificateperiod = cursor.getInt(cursor.getColumnIndex("period")).toString()
            str_certificateetc = cursor.getString((cursor.getColumnIndex("etc"))).toString()
        }

        cursor.close()
        sqlitedb.close()
        certificate.close()

        tvCertificateName.text = str_certificatename
        tvCertificateDate.text = str_certificatedate
        tvCertificatePeriod.text = str_certificateperiod
        tvCertificateEtc.text = str_certificateetc
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_certificate_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
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
            R.id.action_certificate_revise ->{
                val intent = Intent(this,ReviseCertificateActivity::class.java)
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