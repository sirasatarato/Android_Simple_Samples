package com.silso.chatappusingfirebase

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : AppCompatActivity() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.reference
    private var roomName = ""
    private var userName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        roomName = intent.getStringExtra("roomName") ?: "defaultRoom"
        userName = intent.getStringExtra("userName") ?: "defaultUser"
        findChatRoom()
        sendMessage()
    }

    private fun findChatRoom() {
        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1)
        chat_view.adapter = adapter

        databaseReference.child("chat").child(roomName)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    addMessage(dataSnapshot, adapter)
                    Log.e("LOG", "s:$s")
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                    removeMessage(dataSnapshot, adapter)
                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }

    private fun addMessage(dataSnapshot: DataSnapshot, adapter: ArrayAdapter<String>) {
        val chatData: ChatData? = dataSnapshot.getValue(ChatData::class.java)
        adapter.add(chatData?.userName + " : " + chatData?.message)
    }

    private fun removeMessage(dataSnapshot: DataSnapshot, adapter: ArrayAdapter<String>) {
        val chatData: ChatData? = dataSnapshot.getValue(ChatData::class.java)
        adapter.remove(chatData?.userName + " : " + chatData?.message)
    }

    private fun sendMessage() {
        chat_send.setOnClickListener {
            val message = chat_edit.text.toString()

            if (message.isNotBlank()) {
                val chat = ChatData(userName, chat_edit.text.toString())
                databaseReference.child("chat").child(roomName).push().setValue(chat)
                chat_edit.setText("")
            }
        }
    }
}