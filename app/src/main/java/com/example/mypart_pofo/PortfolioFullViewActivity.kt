package com.example.mypart_pofo

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PortfolioFullViewActivity : AppCompatActivity(){

    //DB관련
    lateinit var portfolio: PorflioManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var mRecyclerView : RecyclerView

    var actList = ArrayList<Act>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio_full_view)

        supportActionBar?.setTitle("활동별 모아보기")

        portfolio = PorflioManager(this,"portfolio",null,1)
        sqlitedb = portfolio.readableDatabase

        var actName:String=""
        var actDate_Start :String=""
        var actDate_End :String=""
        var actImage :String=""


        // Spinner 선언
        val spinner : Spinner = findViewById(R.id.spinner)

        //어댑터 설정
        var sData = resources.getStringArray(R.array.list_array)
        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,sData)
        spinner.adapter = adapter

        //어탭터랑 연결
        mRecyclerView = findViewById(R.id.mRecyclerView)
        val mAdapter = recycle_Adapter(this, actList) { act ->
            val intent = Intent(this, PortfolioViewActivity::class.java)
            intent.putExtra("intent_name", actName)
            startActivity(intent)
        }


        val lm = LinearLayoutManager(this)

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

                Toast.makeText(this@PortfolioFullViewActivity,"선택된 항목: "+spinner.getItemAtPosition(position),Toast.LENGTH_SHORT).show()

               var selected_sort :String = spinner.getItemAtPosition(position).toString()  //text 에 스피너의 값이 String으로 가져와집니다.
                Log.d("myDB","selected_sort= "+selected_sort)


                //항목별로 모아보기

                portfolio = PorflioManager(this@PortfolioFullViewActivity,"portfolio",null,1)
                sqlitedb = portfolio.readableDatabase

                //var selected_sort :String =""

                var cursor2 : Cursor
                if(selected_sort=="전체"){
                    cursor2=sqlitedb.rawQuery("SELECT * FROM portfolio;", null)
                }else {

                    cursor2 = sqlitedb.rawQuery("SELECT * FROM portfolio WHERE sort = '" + selected_sort + "';", null)
                }

                //리사이클러뷰를 새롭게 지우고 해당하는 종류의 대외활동을 보여준다.
                actList.clear()


                while(cursor2.moveToNext()){


                        actName = cursor2.getString(cursor2.getColumnIndex("name")).toString()
                        actDate_Start = cursor2.getString(cursor2.getColumnIndex("startDate")).toString()
                        actDate_End = cursor2.getString(cursor2.getColumnIndex("EndDate")).toString()
                        actImage = cursor2.getString(cursor2.getColumnIndex("image")).toString()

                        actList.add(Act(actName,actDate_Start,actDate_End,actImage))


                }

                mRecyclerView.adapter = mAdapter

                //val lm = LinearLayoutManager(this)
                mRecyclerView.layoutManager = lm
                mRecyclerView.setHasFixedSize(true)

                cursor2.close()
                sqlitedb.close()
                portfolio.close()

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
