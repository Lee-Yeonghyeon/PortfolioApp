package com.example.portfolioapp.portCalendar


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.portfolioapp.R

/*
달력화면 - 활동
리스트뷰에 넣어 줄 요소를 View와 연결해주는 어댑터를 생성해주는 클래스
BaseAdapte를 상속받음
 */
class RecordListAdapter (val context: Context, val recordList: ArrayList<ItemRecord>) : BaseAdapter() {

    // xml 파일의 ListView와 데이터를 연결해주는 BaseAdapter의 필수 메소드
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_calendarview, null)


        val tv_record_title = view.findViewById<TextView>(R.id.tv_record_title)
        val tv_record_startdate = view.findViewById<TextView>(R.id.tv_record_startdate)
        val tv_record_enddate = view.findViewById<TextView>(R.id.tv_record_enddate)
        val tv_record_content = view.findViewById<TextView>(R.id.tv_record_content)



        // ArrayList<record>의 변수 record의 데이터를 textview에 담기
        val itemRecord = recordList[position]
        tv_record_title.text = itemRecord.r_title
        tv_record_startdate.text = itemRecord.r_startdate
        tv_record_enddate.text = itemRecord.r_enddate
        tv_record_content.text = itemRecord.r_content

        return view
    }

    // 해당 위치의 item을 선택하는 BaseAdapter의 필수 메소드
    override fun getItem(position: Int): Any {
        return recordList[position]
    }

    // 해당 위치의 item id를 반환하는 BaseAdapter의 필수 메소드
    override fun getItemId(position: Int): Long {
        return 0    // 실질적으로 id가 필요하지 않아 0을 반환하도록 설정
    }

    // ListView에 속한 item의 수를 반환하는 BaseAdapter의 필수 메소드
    override fun getCount(): Int {
        return recordList.size
    }

}
