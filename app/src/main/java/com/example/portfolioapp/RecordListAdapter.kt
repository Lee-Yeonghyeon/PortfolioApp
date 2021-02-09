package com.example.portfolioapp


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class RecordListAdapter (val context: Context, val recordList: ArrayList<ItemRecord>) : BaseAdapter() {


    // xml 파일의 View와 데이터를 연결하는 핵심 역할을 하는 메소드
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_calendarview, null)


        val tv_record_title = view.findViewById<TextView>(R.id.tv_record_title)
        val tv_record_date = view.findViewById<TextView>(R.id.tv_record_date)
        val tv_record_content = view.findViewById<TextView>(R.id.tv_record_content)



        // ArrayList<record>의 변수 record의 데이터를 textview에 담기
        val itemRecord = recordList[position]
        tv_record_title.text = itemRecord.r_title
        tv_record_date.text = itemRecord.r_date
        tv_record_content.text = itemRecord.r_content

        return view
    }

    // 해당 위치의 item 메소드?
    override fun getItem(position: Int): Any {
        return recordList[position]
    }

    // 해당 위치의 item id를 반환하는 메소드
    // id가 필요하지 않으면 0 반환
    override fun getItemId(position: Int): Long {
        return 0
    }

    // ListView에 속한 item의 전체 수를 반환
    // id가 필요하지
    override fun getCount(): Int {
        return recordList.size
    }

}
