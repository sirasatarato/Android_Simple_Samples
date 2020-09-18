package com.example.comy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_base.*


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        textView.setTextColor(AppManager.themeColor)
        button.setTextColor(AppManager.themeColor)

        when(AppManager.appType) {
            AppManager.AppType.RedApp -> textView.text = "레드앱에서 실행시킨 세컨액티비티"
            AppManager.AppType.BlueApp -> textView.text = "블루앱에서 실행시킨 세컨액티비티"
        }

        button.text = "닫기"
        button.setOnClickListener { finish() }
    }
}