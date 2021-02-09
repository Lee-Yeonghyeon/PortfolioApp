package com.example.portfolioapp

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity: AppCompatActivity() {

    lateinit var listview: ListView
    lateinit var homePortDBManager: CertificateManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var certificateview: TextView
    lateinit var portfolioview: TextView

    lateinit var str_prizeTitle: String
    lateinit var str_prizeDate: String

    var prizeList = ArrayList<ItemPrize>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 리스트 속 textview 클릭시
        certificateview = findViewById(R.id.tv_certificateview)
        portfolioview = findViewById(R.id.tv_portfolioview)

        certificateview.setOnClickListener{
            val intent = Intent(this,CertificateViewActivity::class.java)
            startActivity(intent)
        }

        portfolioview.setOnClickListener{
            val intent = Intent(this,PortfolioCalendarViewActivity::class.java)
            startActivity(intent)
        }


        // 자격증/수상 요약부분
        homePortDBManager = CertificateManager(this, "certificate", null, 1)
        sqlitedb = homePortDBManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM certificate;", null)

        while (cursor.moveToNext()) {
            do {
                str_prizeTitle = cursor.getString(cursor.getColumnIndex("name")).toString()
                str_prizeDate = cursor.getString(cursor.getColumnIndex("date")).toString()

                prizeList.add(ItemPrize(str_prizeTitle, str_prizeDate))
            } while (cursor.moveToNext())
        }

        val prizeAdapter = PrizeListAdapter(this, prizeList)
        val prizeListView = findViewById<ListView>(R.id.homeprizeListView)
        prizeListView.adapter = prizeAdapter



        // 리스트뷰 항목 클릭시
        listview = findViewById<ListView>(R.id.homeprizeListView)
        listview.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, CertificateListActivity::class.java)
            intent.putExtra("intent_name", str_prizeTitle)
            startActivity(intent)
        }

    }
}
