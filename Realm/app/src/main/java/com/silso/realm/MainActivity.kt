package com.silso.realm

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.textColor

class MainActivity : AppCompatActivity() {
    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)
        realm = Realm.getDefaultInstance()  // 현재 스레드에서 Realm의 인스턴스 가져오기
        result.text = realm.where<model>(model::class.java).findFirst()?.str ?: "None"
    }

    fun onClick(view: View){
        realm.beginTransaction()

        //데이터베이스에 저장할 객체 생성
        val mod: model = realm.createObject(model::class.java)
        mod.str = input.text.toString()

        realm.commitTransaction()

        result.textColor = Color.BLACK
        result.text = realm.where<model>(model::class.java).findFirst()?.str ?: ""
    }
}