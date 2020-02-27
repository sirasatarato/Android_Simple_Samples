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
        // 현재 스레드에서 Realm의 인스턴스 가져오기
        realm = Realm.getDefaultInstance()
        result.text = realm.where<model>(model::class.java).findFirst()?.str ?: "Help Me..."
    }

    fun onClick(view: View){
        realm.beginTransaction()

        val mod: model = realm.createObject(model::class.java)//데이터베이스에 저장할 객체 생성
        mod.apply {
            this.str = input.text.toString()
        }

        realm.commitTransaction()

        result.textColor = Color.BLACK
        result.text = realm.where<model>(model::class.java).findFirst()?.str ?: ""
    }
}