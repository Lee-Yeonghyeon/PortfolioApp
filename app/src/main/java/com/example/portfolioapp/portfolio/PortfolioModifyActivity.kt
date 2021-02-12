package com.example.portfolioapp.portfolio
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.portfolioapp.R
import com.example.portfolioapp.certprizeFullView.CertificateViewActivity
import com.example.portfolioapp.home.HomeActivity
import com.example.portfolioapp.portFullView.PortfolioFullViewActivity
import java.util.*


class PortfolioModifyActivity  : AppCompatActivity() {

    //DB관련
    lateinit var portfolio: PorflioManager
    lateinit var sqlitedb: SQLiteDatabase

    //수정에 필요한 변수
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
    lateinit var edt_modifyP_url : EditText
    lateinit var str_modify_url : String

    //네비게이션 바
    lateinit var nav_portfolio: ImageView
    lateinit var nav_home: ImageView
    lateinit var nav_certificate: ImageView

    //갤러리 관련
    private val OPEN_GALLERY = 1

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
        edt_modifyP_url = findViewById(R.id.edt_modifyP_url)

        //네비게이션바
        nav_portfolio = findViewById(R.id.nav_portfolio)
        nav_home = findViewById(R.id.nav_home)
        nav_certificate = findViewById(R.id.nav_certificate)

        // 아래 버튼 3개
        nav_portfolio.setOnClickListener {
            val intent = Intent(this, PortfolioFullViewActivity::class.java)
            startActivity(intent)
        }
        nav_home.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        nav_certificate.setOnClickListener {
            val intent = Intent(this, CertificateViewActivity::class.java)
            startActivity(intent)
        }


        //스피너 연결
        var sData = resources.getStringArray(R.array.list_array)
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sData)
        spinner_modifyP_sort.adapter = adapter

        //스피너_해당 종류 선택시 출력
        spinner_modifyP_sort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when (position) {
                    0 -> "전체"
                    1 -> "대외활동"
                    2 -> "공모전"
                    3 -> "동아리"
                    4 -> "교내활동"
                    5 -> "기타"
                    else -> null
                }


                Toast.makeText(this@PortfolioModifyActivity, adapter.getItem(position) + "선택했습니다", Toast.LENGTH_SHORT).show()
                //선택한 항목 토스트 메세지 출력
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("활동 종류를 선택하세요")
            }

        }


        //뒤로가기
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)


        supportActionBar?.setTitle("활동 수정하기")

        //날짜선택 시 캘린더 나오기(시작날짜)
        btn_modifyP_selectDateStart.setOnClickListener {
            var today = Calendar.getInstance()
            var year: Int = today.get(Calendar.YEAR)
            var month: Int = today.get(Calendar.MONTH)
            var date: Int = today.get(Calendar.DATE)

            var listener =
                    DatePickerDialog.OnDateSetListener { view: DatePicker?, year, month, date ->
                        modifycalendarTextViewStart.text = "${year}년 ${month + 1}월 ${date}일"
                    }
            var picker = DatePickerDialog(this, listener, year, month, date)
            picker.show()
        }

        //날짜선택 시 캘린더 나오기(종료날짜)
        btn_modifyP_selectDateEnd.setOnClickListener {
            var today = Calendar.getInstance()
            var year: Int = today.get(Calendar.YEAR)
            var month: Int = today.get(Calendar.MONTH)
            var date: Int = today.get(Calendar.DATE)

            var listener =
                    DatePickerDialog.OnDateSetListener { view: DatePicker?, year, month, date ->
                        modifycalendarTextViewEnd.text = "${year}년 ${month + 1}월 ${date}일"
                    }
            var picker = DatePickerDialog(this, listener, year, month, date)
            picker.show()
        }



        //DB
        portfolio = PorflioManager(this, "portfolio", null, 1)
        sqlitedb = portfolio.writableDatabase



        //사진첨부 클릭시
        btn_modifyP_picture.setOnClickListener {
            openGallery()
        }

        //수정완료 버튼 클릭시
        btn_modifyP_complete.setOnClickListener {
            var str_modi_name : String = edt_modifyP_name.text.toString()
            var str_modi_starDate : String = ""
            var str_modi_endDate :String = ""
            var str_modi_sort: String = spinner_modifyP_sort.toString()
            var str_modi_content : String = edt_modifyP_content.text.toString()
            var str_modi_image : String = ""
            var str_modi_url : String = edt_modifyP_url.text.toString()

            if (modifycalendarTextViewStart.text !== null) {
                str_modi_starDate = modifycalendarTextViewStart.text.toString()
            }

            if (modifycalendarTextViewEnd.text !== null) {
                str_modi_endDate = modifycalendarTextViewEnd.text.toString()
            }

            sqlitedb = portfolio.writableDatabase
            sqlitedb.execSQL(  //수정 시 관련 내용 DB에 UPDATE
                    "UPDATE portfolio SET startDate='" + str_modi_starDate + "', EndDate='"
                            + str_modi_endDate + "',sort='" + str_modi_sort+"', content='"
                            +str_modi_content + "',image='" + str_modi_image+"', url='"
                            +str_modi_url + "' WHERE name = '" + str_modi_name + "';"
            )

            //수정완료 토스트 메세지
            Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show()

            sqlitedb.close()
            portfolio.close()

            val intent = Intent(this, PortfolioFullViewActivity::class.java) //수정 완료 시 활동 모아보기로 이동
            startActivity(intent)

        }


        Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show()

        sqlitedb.close()
        portfolio.close()

    }


    private fun openGallery() {

        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, OPEN_GALLERY)
    }

    //image_url 절대경로 가져오기
    fun getFullPathFromUri(fileUri: Uri?): String? {
        var fullPath: String? = null
        val column = "_data"
        var cursor: Cursor = getContentResolver().query(fileUri!!, null, null, null, null)!!
        if (cursor != null) {
            cursor.moveToFirst()
            var document_id = cursor.getString(0)
            if (document_id == null) {
                for (i in 0 until cursor.columnCount) {
                    if (column.equals(cursor.getColumnName(i), ignoreCase = true)) {
                        fullPath = cursor.getString(i)
                        break
                    }
                }
            } else {
                document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
                cursor.close()
                val projection = arrayOf(column)
                try {
                    cursor = getContentResolver().query(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            projection,
                            MediaStore.Images.Media._ID + " = ? ",
                            arrayOf(document_id),
                            null
                    )!!
                    if (cursor != null) {
                        cursor.moveToFirst()
                        fullPath = cursor.getString(cursor.getColumnIndexOrThrow(column))
                    }
                } finally {
                    cursor?.close()
                }
            }
        }
        return fullPath
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == OPEN_GALLERY) {
                var dataUri = data?.dataString
                Log.d("myDB", "url: " + dataUri)
                var currentImageUrl: Uri? = data?.data

                //imageUri = absolutelyPath(currentImageUrl!!)  //사진 uri정보를 imageUri가 가지고 있음
                str_modify_url = getFullPathFromUri(currentImageUrl!!)!!
                Log.d("myDB", "aburl: " + dataUri)

                try {
                    var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, currentImageUrl)
                    modifyimageView.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            Log.d("ActivityResult", "something wrong")
        }

    }

    //뒤로가기 버튼 클릭시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home ->{
                val intent = Intent(this,PortfolioFullViewActivity::class.java)
                startActivity(intent)
                return true
            }
            else->{
                return super.onOptionsItemSelected(item)
            }
        }
    }


}