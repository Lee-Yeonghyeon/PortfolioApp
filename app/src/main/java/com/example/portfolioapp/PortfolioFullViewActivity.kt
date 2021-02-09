package com.example.mypart_pofo

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.portfolioapp.R


class PortfolioFullViewActivity : AppCompatActivity(){

    //DB관련
    lateinit var portfolio: PorflioManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var view : RecyclerView
    lateinit var layout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio_full_view)

        supportActionBar?.setTitle("포트폴리오 활동별로보기")

        portfolio = PorflioManager(this,"portfolio",null,1)
        sqlitedb = portfolio.readableDatabase

        layout = findViewById(R.id.portfolioAll)

        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM portfolio;",null)

        var num: Int =0
        while(cursor.moveToNext()){
            var actName = cursor.getString(cursor.getColumnIndex("name")).toString()
            var actDate_Start = cursor.getString(cursor.getColumnIndex("startDate")).toString()
            var actDate_End = cursor.getString(cursor.getColumnIndex("EndDate")).toString()
            var actSort = cursor.getString(cursor.getColumnIndex("sort")).toString()
            var actContent = cursor.getString(cursor.getColumnIndex("content")).toString()
            var actImage = cursor.getString(cursor.getColumnIndex("image")).toString()

            var layout_item : LinearLayout = LinearLayout(this)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.setPadding(20,10,20,10)
            layout_item.id = num
            layout_item.setTag(actName)

            var tvName: TextView = TextView(this)
            tvName.text = actName
            tvName.textSize = 30F
            tvName.setBackgroundColor(Color.LTGRAY)
            layout_item.addView(tvName)

            var tvDateStart : TextView = TextView(this)
            tvDateStart.text = actDate_Start
            layout_item.addView(tvDateStart)

            var tvDateEnd : TextView = TextView(this)
            tvDateEnd.text = actDate_End
            layout_item.addView(tvDateEnd)

            var tvSort : TextView = TextView(this)
            tvSort.text = actSort
            layout_item.addView(tvSort)

            var tvContent : TextView = TextView(this)
            tvContent.text = actContent
            layout_item.addView(tvContent)

            var tvImage : TextView = TextView(this)
            tvImage.text = actImage
            layout_item.addView(tvImage)

            layout_item.setOnClickListener {
                val intent = Intent(this,PortfolioViewActivity::class.java)
                intent.putExtra("intent_name",actName)
                startActivity(intent)
            }

            layout.addView(layout_item)
            num++;

        }

        cursor.close()
        sqlitedb.close()
        portfolio.close()



        // Spinner 선언
        val spinner : Spinner = findViewById(R.id.spinner)

        //어댑터 설정
        var sData = resources.getStringArray(R.array.list_array)
        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,sData)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when(position) {
                    0   ->  "전체"
                    1   ->  "대외활동"
                    2   ->  "공모전"
                    3   ->  "동아리"
                    4   ->  "교내활동"
                    5   ->  "기타"
                    else -> null
                }

                //spinner 제대로 선택된 것  확인하는 방법
                var myHero = spinner.selectedItem //요렇게...
                println(myHero)


                Toast.makeText(this@PortfolioFullViewActivity,adapter.getItem(position)+"선택했습니다",Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("활동 종류를 선택하세요")
            }

        }











    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { //각각 포트폴리오 기록창, PortfolioFullView 창으로 이동할 수 있도록 연결해주세요(액션바에 있는 아이콘임)
        menuInflater.inflate(R.menu.forfullview, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.addPortfolio -> {
                val intent = Intent(this,WritePortfolioActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.moveCalendar -> {
                val intent = Intent(this,PortfolioViewActivity::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }


}