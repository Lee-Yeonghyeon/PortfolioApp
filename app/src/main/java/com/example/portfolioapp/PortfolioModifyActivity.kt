package com.example.portfolioapp
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mypart_pofo.PorflioManager

class PortfolioModifyActivity  : AppCompatActivity() {

    lateinit var portfolio: PorflioManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var str_actName : String

    lateinit var edt_modifyP_name : EditText
    lateinit var btn_modifyP_selectDateStart : Button
    lateinit var modifycalendarTextViewStart : TextView
    lateinit var btn_modifyP_selectDateEnd : Button
    lateinit var modifycalendarTextViewEnd : TextView
    lateinit var spinner_modifyP_sort :Spinner
    lateinit var edt_modifyP_content : EditText
    lateinit var btn_modifyP_picture : Button
    lateinit var modifyimageView : ImageView
    lateinit var btn_modifyP_complete : Button

    lateinit var str_modify_actName : String
    lateinit var str_modify_actDay_Start : String
    lateinit var str_modify_actDay_End : String
    lateinit var str_modify_actSort : String
    lateinit var str_modify_actContent : String
    lateinit var str_modify_image : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio_modify_view)

        //변수와 위젯 연결
        edt_modifyP_name = findViewById(R.id.edt_modifyP_name)
        btn_modifyP_selectDateStart= findViewById(R.id.btn_modifyP_selectDateStart)
        modifycalendarTextViewStart= findViewById(R.id.modifycalendarTextViewStart)
        btn_modifyP_selectDateEnd= findViewById(R.id.btn_modifyP_selectDateEnd)
        modifycalendarTextViewEnd= findViewById(R.id.modifycalendarTextViewEnd)
        spinner_modifyP_sort= findViewById(R.id.spinner_modifyP_sort)
        edt_modifyP_content= findViewById(R.id.edt_modifyP_content)
        btn_modifyP_picture= findViewById(R.id.btn_modifyP_picture)
        modifyimageView = findViewById(R.id.modifyimageView)
        btn_modifyP_complete = findViewById(R.id.btn_modifyP_complete)


        val intent= intent
        str_actName = intent.getStringExtra("intent_name")!!  //intent로 넘긴 이름을 str_actName에 저장

        portfolio = PorflioManager(this, "portfolio", null, 1)
        sqlitedb = portfolio.writableDatabase
        var cursor : Cursor

        cursor = sqlitedb.rawQuery("SELECT * FROM portfolio WHERE name = '"+str_actName+"';",null)
        cursor.moveToFirst()


        str_modify_actDay_Start  =""
        str_modify_actDay_End=""
        str_modify_actSort=""
        str_modify_actContent=""



        if(cursor.moveToNext()){
            if(str_actName==cursor.getString(cursor.getColumnIndex("name")).toString()){


                str_modify_actDay_Start = cursor.getString(cursor.getColumnIndex("startDate")).toString()
                str_modify_actDay_End = cursor.getString(cursor.getColumnIndex("EndDate")).toString()
                str_modify_actSort = cursor.getString(cursor.getColumnIndex("sort")).toString()
                str_modify_actContent = cursor.getString(cursor.getColumnIndex("content")).toString()
                str_modify_image = cursor.getString(cursor.getColumnIndex("image")).toString()



                sqlitedb.execSQL("UPDATE FROM portfolio WHERE name = '" + str_actName + "';")
            }
        }

        Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show()

        sqlitedb.close()
        portfolio.close()

    }
}