package com.example.portfolioapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.setTitle("홈")

        fun onNavigationSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.nav_portfolio -> {
                    var intent = Intent(this, PortfolioCalendarViewActivity::class.java)
                    startActivity(intent)
                    return true
                }
                R.id.nav_home -> {
                    var intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    return true
                }
                R.id.nav_certificate -> {
                    var intent = Intent(this, CertificateViewActivity::class.java)
                    startActivity(intent)
                    return true
                }
            }
            return false
        }
    }
}
