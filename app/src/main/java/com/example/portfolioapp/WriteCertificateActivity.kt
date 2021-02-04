package com.example.portfolioapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class WriteCertificateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_certificate)

        supportActionBar?.setTitle("자격증/수상경력 입력하기")
    }
}