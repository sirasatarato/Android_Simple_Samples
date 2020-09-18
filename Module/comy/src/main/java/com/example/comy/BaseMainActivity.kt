package com.example.comy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_base.*

open class BaseMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        button.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}