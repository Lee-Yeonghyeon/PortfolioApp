package com.example.mypart_pofo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class PortfolioFullViewActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio_full_view)

        supportActionBar?.setTitle("포트폴리오 활동별로보기")

        // Spinner 선언
        val spinner : Spinner = findViewById(R.id.spinner)

        //어댑터 설정 방법1-resource-array.xml에 있는 아이템 목록을 추가한다.
        //spinner.adapter = ArrayAdapter.createFromResource(this,R.array.list_array, android.R.layout.simple_spinner_item)

        //어댑터 설정 방법2
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