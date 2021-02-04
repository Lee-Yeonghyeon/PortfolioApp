package com.example.portfolioapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

class CertificateViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificate_view)

        supportActionBar?.setTitle("자격증/수상경력")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { //자격증, 수상내역 기록창으로 이동할 수 있도록 연결해주세요(액션바에 있는 아이콘임)
        menuInflater.inflate(R.menu.forcertificateview, menu)
        return true
    }
}
