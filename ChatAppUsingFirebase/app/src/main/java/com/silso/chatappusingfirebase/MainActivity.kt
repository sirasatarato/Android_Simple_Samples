package com.silso.chatappusingfirebase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enter_room_button.setOnClickListener {
            val userName = user_name_edit.text.toString()
            val roomName = room_name_edit.text.toString()

            if (userName.isNotBlank() && roomName.isNotBlank()) {
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("roomName", roomName)
                intent.putExtra("userName", userName)
                startActivity(intent)
            }
        }
    }
}