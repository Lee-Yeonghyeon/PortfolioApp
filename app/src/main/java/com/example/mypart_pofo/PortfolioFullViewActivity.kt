package com.example.mypart_pofo

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PortfolioFullViewActivity : AppCompatActivity(){

    //DB관련
    lateinit var portfolio: PorflioManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var mRecyclerView : RecyclerView
    lateinit var actName : String

    var actList = ArrayList<Act>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio_full_view)

        supportActionBar?.setTitle("포트폴리오 활동별로보기")

        portfolio = PorflioManager(this,"portfolio",null,1)
        sqlitedb = portfolio.readableDatabase


        mRecyclerView = findViewById(R.id.mRecyclerView)

        //val intent= intent
        //actName = intent.getStringExtra("intent_name")!!


        var actName:String=""
        var actDate_Start :String=""
        var actDate_End :String=""
        var actImage :String=""


        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM portfolio;",null)
        // WHERE name = '"+actName+"'
        //actList = sqlitedb.rawQuery("SELECT * FROM portfolio;",null)


        var num: Int =0
        while(cursor.moveToNext()){
            cursor.moveToFirst()
            actName = cursor.getString(cursor.getColumnIndex("name")).toString()
            actDate_Start = cursor.getString(cursor.getColumnIndex("startDate")).toString()
            actDate_End = cursor.getString(cursor.getColumnIndex("EndDate")).toString()
            actImage = cursor.getString(cursor.getColumnIndex("image")).toString()

            actList.add(Act(actName,actDate_Start,actDate_End,actImage))

        }

        //어탭터랑 연결...?
        val mAdapter = recycle_Adapter(this, actList){ act ->
            val intent = Intent(this, PortfolioViewActivity::class.java)
            intent.putExtra("intent_name", actName)
            startActivity(intent)
        }
        /* 람다식{(Act) -> Unit} 부분을 추가하여 itemView의 setOnClickListener 에서 어떤 액션을 취할 지 설정해준다. */
        mRecyclerView.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        mRecyclerView.layoutManager = lm
        mRecyclerView.setHasFixedSize(true)


        cursor.close()
        sqlitedb.close()
        portfolio.close()


        //dogList에 데이터를 추가
        var dogList = arrayListOf<Act>(
            Act("actName","actDate_Start","actDate_End","actImage")
        )


        // Spinner 선언
        val spinner : Spinner = findViewById(R.id.spinner)

        //어댑터 설정
        var sData = resources.getStringArray(R.array.list_array)
        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,sData)
        spinner.adapter = adapter


         /*
       mRecyclerView. set{ parent, view, position, id ->
            val intent = Intent(this, PortfolioViewActivity::class.java)
            intent.putExtra("intent_name", str_prizeTitle)
            startActivity(intent)
        }*/

 


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
                //var actSort = spinner.selectedItem //요렇게...
                var sortName : String = adapter.getItem(position).toString()
                Log.d("myDB", "sort_Name : " + sortName)

                Toast.makeText(this@PortfolioFullViewActivity,adapter.getItem(position)+"선택했습니다",Toast.LENGTH_SHORT).show()

                portfolio = PorflioManager(this@PortfolioFullViewActivity,"portfolio",null,1)
                sqlitedb = portfolio.readableDatabase


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("활동 종류를 선택하세요")
            }

        }

        /*btn_Specify.setOnClickListener {
            val intent = Intent(this, PortfolioViewActivity::class.java)
            startActivity(intent)
        }*/

    }

    /*
    private fun selectSort(): String {

        var cursor2 : Cursor

        portfolio = PorflioManager(this, "portfolio", null, 1)
        sqlitedb = portfolio.readableDatabase
        cursor2= sqlitedb.rawQuery( "SELECT FROM portfolio WHERE sort = '"+str_actName+"';",null)

        while (cursor1.moveToNext()) {

            var imageurl = cursor1.getString(cursor1.getColumnIndex("image")).toString()
            photouri = imageurl
            Log.d("myDB","photouri: " + photouri)

        }

        return photouri

        cursor1.close()
        sqlitedb.close()
        portfolio.close()

    }
*/










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
