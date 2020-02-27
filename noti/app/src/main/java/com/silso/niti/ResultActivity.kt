package com.silso.niti

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val text = "전달 받은 값은"
        var id = 0

        val extras: Bundle? = intent.extras
        if (extras != null) {
            id = extras.getInt("notificationId")
        }

        textView.text = "$text $id"

        val notificationManager: NotificationManager =  getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //노티피케이션 제거
        notificationManager.cancel(id)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}
