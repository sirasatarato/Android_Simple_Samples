package com.silso.room.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.silso.room.InputMsgRepository
import com.silso.room.R
import com.silso.room.room.AppDatabase
import com.silso.room.room.InputMsg
import com.silso.room.vm.MailViewModelFactory
import com.silso.room.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeUi()
    }

    private fun subscribeUi() {
        /**
         * 이 과정은 DI(dependency injection)으로 대체가 가능
         * ok
         */
        val dao = AppDatabase.getInstance(this).inputMsgDao()
        val repository =
            InputMsgRepository.getInstance(dao)
        val factory = MailViewModelFactory(repository)
        val viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        viewModel.inputMsgs.observe(this, Observer {
            if (it == null || it.isEmpty())
                return@Observer

            val sb = StringBuffer()
            for (data in it) {
                sb.append(data.msg).append("\n")
            }

            tv_result.text = sb.toString()
        })

        btn_input.setOnClickListener {
            val input = et_input.text.toString()
            if (input.isEmpty())
                return@setOnClickListener

            et_input.setText("")
            viewModel.insertMsg(InputMsg(msg = input))
        }
    }

    override fun onBackPressed() {
        tv_result.text = ""
    }
}
